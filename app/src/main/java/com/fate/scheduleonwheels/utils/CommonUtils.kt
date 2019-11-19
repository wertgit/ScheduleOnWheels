package com.fate.scheduleonwheels.utils

import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.data.di.entities.Shift
import com.fate.data.di.entities.WeekDay
import java.util.*


class CommonUtils {

    companion object {

        fun generateSchedule(list: List<Engineer>): ArrayList<Schedule> {

            val scheduleList: ArrayList<Schedule> = arrayListOf()
            val mapEngineers = createHashMap(list)
            val engineerIds = ArrayList(mapEngineers.keys)
            val weekdays = generateWeekDays(engineerIds)

            /**
             * creates a whole Schedule by looping through the weekdays
             */
            for (i in weekdays.indices) {

                val chosenListOfEngineers = mutableListOf<Engineer>()
                val chosenIds = weekdays[i].ids

                // loop through ids mapping with Engineer HashMap
                for (j in chosenIds.indices) {
                    chosenListOfEngineers.add(mapEngineers[chosenIds[j]]!!)
                }

                val listShifts = generateShifts(chosenListOfEngineers).toList()

                val weekdaySchedule = Schedule(
                    weekdays[i].date,
                    weekdays[i].week,
                    chosenListOfEngineers.toList(),
                    listShifts
                )

                scheduleList.add(weekdaySchedule)

            }

            return scheduleList

        }


        private fun generateWeekDays(list: MutableList<Int>): ArrayList<WeekDay> {

            val weekdaysList: ArrayList<WeekDay> = arrayListOf()
            val shiftsArray =
                intializeArray()  // multidimensional array representing the shifts to fill in a week.
            var filteredArray: ArrayList<Array<Int>>

            // splitList list into two
            val splitList = splitList(list)

            for (x in splitList.indices) {

                var prevSelection: Int = -1
                /// Assign Group
                val group = splitList[x]

                val hashMapOfTotalShiftsEach: HashMap<Int, Int> = hashMapOf()
                // Create Weight HasMap
                for (i in group.indices) {
                    hashMapOfTotalShiftsEach[group[i]] = 0
                }

                // Iterate picking out Random Numbers.
                for (j in shiftsArray.indices) {

                    for (i in shiftsArray[j].indices) {

                        val listCopy = group.toMutableList()  // make a copy of the group list

                        if (!hashMapOfTotalShiftsEach.isNullOrEmpty() && listCopy.size > 1) {
                            // removes items that appear more than twice
                            listCopy.removeIf {
                                hashMapOfTotalShiftsEach[it]!! > 1
                            }
                        }

                        if (prevSelection != -1 && listCopy.size > 1) {
                            // remove arleady chosen Items to Obey Rule 3
                            listCopy.removeIf {
                                it == prevSelection
                            }
                        }

                        val random = getRandomItemFromList(listCopy)
                        prevSelection = random
                        // Recording Weights to the HashMap
                        hashMapOfTotalShiftsEach[prevSelection] =
                            hashMapOfTotalShiftsEach[prevSelection]!!.plus(1)
                        shiftsArray[j][i] = random
                    }
                }


                // Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

                filteredArray = filterArray(shiftsArray)  // To remove Double Shifts

                // Now we Iterate through the days of the week Mon to Fri Assinging Weekkdays
                for (i in weekDays.indices) {

                    val a = filteredArray.first()[i]
                    val b = filteredArray.last()[i]
                    weekdaysList.add(
                        WeekDay(
                            weekDays[i],
                            "Week ${x.plus(1)}",
                            listOf(a, b)
                        )
                    )

                }

            }

            return weekdaysList

        }


        /**
         * Intialzes array by setting the size and intial value.
         * Here the size is 2 by 5 and the value is 0
         */
        private fun intializeArray(): ArrayList<Array<Int>> {
            val output: ArrayList<Array<Int>> = arrayListOf()
            for (i in 0 until 2) {
                var array = arrayOf<Int>()
                for (j in 0..4) {
                    array += 0  // assings zero as intial value
                }
                output += array
            }
            return output
        }

        /**
         * Since we assume Schedule will span two weeks and start on first work day end on last work day.
         * We can assign numbers for a speicifc day of weekDays or better yet use Calendar.DayOFWeek
         * Val since it will not change
         */
       private  val weekDays: ArrayList<Int> = arrayListOf(
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY
        )


        /**
         * returns a Calendar Day name based on the dateId.
         */
        fun getDayName(dateId: Int): String {
            val dayName: HashMap<Int, String> = hashMapOf(
                0 to "Saturday",
                1 to "Sunday",
                2 to "Monday",
                3 to "Tuesday",
                4 to "Wednesday",
                5 to "Thursday",
                6 to "Friday"
            )
            return dayName[dateId].toString()
        }


        private fun performSwap(input: Array<Int>, index_A: Int): Array<Int> {
            val indexB = 1
            val inputCopy = input.copyOf()
            val temp = inputCopy[index_A]
            inputCopy[index_A] = inputCopy[indexB]
            inputCopy[indexB] = temp

            return inputCopy
        }


        /**
         * Filters the array to swap items that appear more than once in a row
         * To avoid double shifts.
         */
        fun filterArray(input: ArrayList<Array<Int>>): ArrayList<Array<Int>> {

            val output: ArrayList<Array<Int>> = arrayListOf()

            val firstRow = input.first()
            val secondRow = input.last()
            var firstRowCopy = firstRow.copyOf()

            for (x in firstRow.indices) {

                if (firstRow[x] == secondRow[x]) {
                    //Timber.d("Found Similar at [$x]")
                    firstRowCopy = performSwap(firstRowCopy, x)
                    //Collections.swap(arrayTopCopy.toList(),x,1)
                }
            }

            output.add(firstRowCopy)
            output.add(secondRow)

            return output

        }

        // Function to splitList a list of Int into two sublists in Java // Based on two weeks
        fun splitList(list: List<Int>): Array<List<Int>> {
            // get size of the list
            val size = list.size
            // construct new list from the returned view by list.subList() method
            val first = ArrayList(list.subList(0, (size + 1) / 2))
            val second = ArrayList(list.subList((size + 1) / 2, size))

            // return an List array to accommodate both lists
            return arrayOf(first, second)
        }

        fun generateShifts(list: List<Engineer>): ArrayList<Shift> {

            val listOfShifts: List<Shift> = listOf(Shift.DAY, Shift.NIGHT)

            val shifts: ArrayList<Shift> = arrayListOf() //
            var shiftPosition = 0

            for (x in list.indices) {
                shifts.add(listOfShifts[shiftPosition])
                // checks to reset the shifPostion when it reaches 1
                if (shiftPosition <= 0) shiftPosition += 1 else shiftPosition = 0
            }
            return shifts
        }


        fun getRandomItemFromList(
            list: List<Int>
        ): Int {
            val pickedItem: Int
            val listToPickFrom: ArrayList<Int> = arrayListOf() //
            var randomIndexNum = 0
            val random = Random()
            listToPickFrom.addAll(list)
            randomIndexNum = random.nextInt(listToPickFrom.size)  // generate a random nmuber
            pickedItem = listToPickFrom[randomIndexNum]  // assign a random engineer

            //Timber.d("Picked:  $pickedItem from list ${listOfEngineers}")
            return pickedItem

        }

        /**
         * Creates and returns hashmap
         */
        fun createHashMap(list: List<Engineer>): HashMap<Int, Engineer> {

            val hashMap: HashMap<Int, Engineer> = hashMapOf()
            // Create Weight HasMap
            for (i in list.indices) {
                hashMap[list[i].id] = list[i]
            }
            return hashMap
        }

    }


}

