package com.fate.scheduleonwheels.utils

import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.data.di.entities.Shift
import com.fate.data.di.entities.WeekDay
import java.util.*


class CommonUtils {

    companion object {


        /**
         * A helper function to generate a list of Schedules
         * The Algorithm is roughly based on the fact that we are dealing with only 10 Engineers
         * and a span of 2 weeks.
         */
        fun generateSchedule(list: List<Engineer>): ArrayList<Schedule> {

            val scheduleList: ArrayList<Schedule> = arrayListOf()
            val mapEngineers = createHashMap(list)
            val engineerIds = ArrayList(mapEngineers.keys)
            val weekdays = generateWeekDays(engineerIds)


            // Creates a Schedule iterating through the weekdays.
            for (i in weekdays.indices) {

                val listOfEngineers = mutableListOf<Engineer>()
                val chosenIds = weekdays[i].ids

                // loop through chosen ids mapping them with Engineer ones.
                for (j in chosenIds.indices) {
                    listOfEngineers.add(mapEngineers[chosenIds[j]]!!)
                }

                val listShifts = generateShifts(listOfEngineers).toList()

                val weekdaySchedule = Schedule(
                    weekdays[i].date,
                    weekdays[i].week,
                    listOfEngineers.toList(),
                    listShifts
                )

                scheduleList.add(weekdaySchedule)

            }

            return scheduleList

        }


        /**
         *   function generates a list of week-days by plotting a shift multidimensional array
         *   filtering the multidimensional array and generates weekday items.
         *  @param list the list of engineers
         * @return a list of week-days
         */
        private fun generateWeekDays(list: MutableList<Int>): List<WeekDay> {

            val weekdaysList: ArrayList<WeekDay> = arrayListOf()
            val shiftsArray =
                intializeArray()  // Multidimensional array representing shifts to fill in a week.
            var filteredArray: ArrayList<Array<Int>>

            // split list into two parts
            val arrayOfLists = splitList(list)

            for (x in arrayOfLists.indices) {

                var prevSelection: Int = -1

                /// assign a part
                val part = arrayOfLists[x]


                // creates HasMap with Engineer ID as key and ammount of shifts assigned as value.
                val mapOfTotalShiftsAssigned: HashMap<Int, Int> = hashMapOf()
                for (i in part.indices) {
                    mapOfTotalShiftsAssigned[part[i]] = 0
                }

                // Iterate picking a random value and plottig it to the multidimensional array
                for (j in shiftsArray.indices) {

                    for (i in shiftsArray[j].indices) {

                        val listCopy = part.toMutableList()

                        if (!mapOfTotalShiftsAssigned.isNullOrEmpty() && listCopy.size > 1) {
                            // removes any engineer that arleady has more than one shifts assgined
                            // so we equally assign shifts.
                            listCopy.removeIf {
                                mapOfTotalShiftsAssigned[it]!! > 1
                            }
                        }

                        if (prevSelection != -1 && listCopy.size > 1) {
                            // removes arleady chosen engineer based on previuos choice
                            // so we avoid state where an engineer works more than once consecutively.
                            listCopy.removeIf {
                                it == prevSelection
                            }
                        }

                        val randomItem = getRandomItemFromList(listCopy)

                        prevSelection = randomItem

                        // record ammount of shifts assigned
                        mapOfTotalShiftsAssigned[prevSelection] =
                            mapOfTotalShiftsAssigned[prevSelection]!!.plus(1)
                        shiftsArray[j][i] = randomItem
                    }
                }

                // Handles situation where an engineer is assigned Double Shifts.
                filteredArray = filterArray(shiftsArray)

                // Iterate through the days of the week Mon to Fri generating a weekday item.
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
         *  function intializes multidimensional array by setting the size and intial value.
         *  The multidimensional array is of size 2 by 5 and the intial value assigned is 0
         */
        private fun intializeArray(): ArrayList<Array<Int>> {
            val output: ArrayList<Array<Int>> = arrayListOf()
            for (i in 0 until 2) {
                var array = arrayOf<Int>()
                for (j in 0..4) {
                    array += 0  // assigns zero as intial value
                }
                output += array
            }
            return output
        }

        /**
         * Since we assume a schedule will span two weeks and start on first work day end on last work day.
         * We assign Calendar days for a speicifc day of weekDays.
         *
         */
        private val weekDays: ArrayList<Int> = arrayListOf(
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
         * function compares values of array in the first and second row.
         * Swaps the values in the first row if they are equal.
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
                }
            }

            output.add(firstRowCopy)
            output.add(secondRow)

            return output

        }

        /**
         *   function to split a list into two sub lists
         *   This function always assumes we dealing with two items
         *  @param list the list to split from.
         * @return a list split into two parts.
         */
        fun splitList(list: List<Int>): Array<List<Int>> {
            // get size of the list
            val size = list.size
            // construct new list from the returned view by list.subList() method
            val first = ArrayList(list.subList(0, (size + 1) / 2))
            val second = ArrayList(list.subList((size + 1) / 2, size))

            // return an List array to accommodate both lists
            return arrayOf(first, second)
        }

        /**
         * function Generate shifts by total number of items in list.
         * @param list the Engineer list to generate based from
         * @return a list of generated shifts.
         */
        private fun generateShifts(list: List<Engineer>): List<Shift> {

            val shifts: ArrayList<Shift> = arrayListOf()
            var shiftToggle = false // switches shift evey iteration
            var shift: Shift

            for (x in list.indices) {
                shiftToggle = !shiftToggle
                shift = if (shiftToggle) Shift.DAY else Shift.NIGHT
                shifts.add(shift)
            }
            return shifts
        }


        /**
         * function to pick a random Item from a list
         * @param list the list to pick from.
         * @return an Int of a random Item.
         */
        private fun getRandomItemFromList(list: List<Int>): Int {
            val pickedItem: Int
            val listToPickFrom: ArrayList<Int> = arrayListOf()
            var randomIndexNum = 0
            val random = Random()
            listToPickFrom.addAll(list)
            randomIndexNum = random.nextInt(listToPickFrom.size)
            pickedItem = listToPickFrom[randomIndexNum]
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

