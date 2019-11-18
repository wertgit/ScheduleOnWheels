package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.utils.CommonUtils
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class ScheduleViewModel() : BaseViewModel() {

    private var dataSchedule: MutableLiveData<ArrayList<Schedule>> = MutableLiveData()
    private var totalNumberOfWeeks = 2
    private var totalEngineersToPickAtRandom = 2

    init {


    }

    fun getDataSchedule(): MutableLiveData<ArrayList<Schedule>> {
        return dataSchedule
    }

    fun generateSchedule(list: List<Engineer>) {

        Timber.d("START")

        var listOfEngineerIds: MutableList<Int> = mutableListOf()

        for (i in list.indices) {
            listOfEngineerIds.add(list[i].id)
        }

        val weekScheduledList: java.util.ArrayList<Schedule> = arrayListOf() //

        val generatedSchdule = splitEngineersIntoGroups(listOfEngineerIds)

        // map Engineers based on the ids to generate a Schedule
        for (i in generatedSchdule.indices) {

            val chosenEngineersList = mutableListOf<Engineer>()

            for (j in list.indices) {

                for (k in generatedSchdule[i].indices) {

                    if (list[j].id == generatedSchdule[i][k]) {
                        chosenEngineersList.add(list[j])
                    }

                }

            }

            // Timber.d("Chosen Enginners: $chosenEngineersList")

            val shiftList = CommonUtils.generateShifts(chosenEngineersList).toList()

            val weekdaySchedule = Schedule(
                4,
                "Week ${1.plus(1)}",
                chosenEngineersList.toList(),
                shiftList
            )

            weekScheduledList.add(weekdaySchedule)

            //Timber.d("Chosen WeekdaySchedule: $weekdaySchedule")
        }

        //Timber.d("The Week: $generatedSchdule")

        //dataSchedule.value = generateSchedules(list, totalNumberOfWeeks,totalEngineersToPickAtRandom)

        dataSchedule.value = weekScheduledList

        Timber.d("END")
    }


    fun swapItemsInArray(multiDimenArray: ArrayList<Array<Int>>){

        for (j in multiDimenArray.indices) {
            for (i in multiDimenArray[j].indices) {
                Timber.d("Position [$j][$i] and item is ${multiDimenArray[j][i]}")
            }
        }

    }

    fun splitEngineersIntoGroups(list: MutableList<Int>): ArrayList<List<Int>> {

        val weekScheduledList: ArrayList<List<Int>> = arrayListOf()

        val weekShiftsArray = arrayListOf<Array<Int>>()  // multidimensional array
        // we want the array be of size 2 by 5
        for (i in 0 until 2) {
            var array = arrayOf<Int>()
            for (j in 0..4) {
                array += 1
            }
            weekShiftsArray += array
        }

//        weekShiftsArray[0][0] = 5
//        weekShiftsArray[0][1] = 4
//        weekShiftsArray[0][2] = 3
//        weekShiftsArray[0][3] = 4
//        weekShiftsArray[0][4] = 2
//        weekShiftsArray[1][0] = 5  1
//        weekShiftsArray[1][1] = 1  5
//        weekShiftsArray[1][2] = 3  2
//        weekShiftsArray[1][3] = 1
//        weekShiftsArray[1][4] = 2

        for (array in weekShiftsArray) {
            for (value in array) {
                print("$value ")
            }
            println()
        }


        // Timber.d("Lisr of Ids: $list")

        //val engineersRawList = mutableListOf(0,1,2,3,4,5,6,7,8,9)
       // val engineersRawList = mutableListOf(1,2,3,4,5,6,7,8,9,10)
        val engineersRawList = list

        // split list into two.
        val splitList = split(engineersRawList)


        for (x in 0 until totalNumberOfWeeks) {

            var previuosChosen: List<Int> = listOf()
            var previuos: Int = -1
            var hashMapOfTotalShiftsEach: HashMap<Int, Int> = hashMapOf()

            /// Assign Group A to First Week
            val groupA = splitList[x] as List<Int>


            //Timber.d("groupA: $groupA")

            // Create
            for (i in groupA.indices) {
                hashMapOfTotalShiftsEach[groupA[i]] = 0
            }


            // Iterate picking out Random Numbers.
            for (j in weekShiftsArray.indices) {
                for (i in weekShiftsArray[j].indices) {

                   // Timber.d("Position [$j][$i]")

                    val listCopy = groupA.toMutableList()  // make a copy of the group list

                    if (!hashMapOfTotalShiftsEach.isNullOrEmpty() && listCopy.size > 1) {
                        // removes items that appear more than 2
                        listCopy.removeIf {
                            hashMapOfTotalShiftsEach[it]!! > 1
                        }
                        //Timber.d("After Remove by Weight: $groupACopy")
                    }

                    if (previuos != -1 && listCopy.size > 1 ) {
                        // remove arleady chosen Items to Obey Rule 3

                        listCopy.removeIf {
                            it == previuos
                        }
                         Timber.d("groupACopy After Remove: $listCopy")
                        //Timber.d("After Remove by Occurance: $groupACopy")
                    }

                    val random = getRandomItemFromList(listCopy)
                    previuos = random
                    // Recording Weights to the HashMap
                    hashMapOfTotalShiftsEach[previuos] = hashMapOfTotalShiftsEach[previuos]!!.plus(1)
                    weekShiftsArray[j][i] = random
                }
            }

//            for (i in weekShiftsArray.indices) {
//                val random = getRandomItemFromList(groupA.toMutableList())
//                //weekShiftsArray[0][i] = random
//            }
            //Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

            // Now we Iterate through the days of the week Mon to Fri Assinging Two people

            Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

            swapItemsInArray(weekShiftsArray)


            break

            for (i in CommonUtils.weekDays.indices) {

                //Timber.d("previuosChosen$i: $previuosChosen")
                val groupACopy = groupA.toMutableList()  // make a copy of the group list

                if (!hashMapOfTotalShiftsEach.isNullOrEmpty() && groupACopy.size > totalEngineersToPickAtRandom) {
                    // remove arleady chosen Items to Obey Rule 3

                    groupACopy.removeIf {
                        hashMapOfTotalShiftsEach[it]!! > 1
                    }
                    //Timber.d("After Remove by Weight: $groupACopy")
                }

                if (!previuosChosen.isNullOrEmpty() && groupACopy.size > totalEngineersToPickAtRandom) {
                    // remove arleady chosen Items to Obey Rule 3

                    groupACopy.removeIf {
                        it == previuosChosen[0] || it == previuosChosen[1]
                    }
                    // Timber.d("groupACopy After Remove: $groupACopy")
                    //Timber.d("After Remove by Occurance: $groupACopy")
                }

                //Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")
                // Timber.d("groupACopy After Remove: $groupACopy")

                val randomChosen = getRandomItems(totalEngineersToPickAtRandom, groupACopy)
                previuosChosen = randomChosen

                // Adding Shift Weights to the HashMap
                for (item in randomChosen) {
                    hashMapOfTotalShiftsEach[item] = hashMapOfTotalShiftsEach.get(item)!!.plus(1)
                }

                weekScheduledList.add(randomChosen)
                //Timber.d("randomChosen$i: $randomChosen")


            }




        }

        //Timber.d("weekScheduledList $weekScheduledList")

        for (array in weekShiftsArray) {
            for (value in array) {
                print("$value ")
            }
            println()
        }

        return weekScheduledList

    }

    // Generic function to split a list into two sublists in Java
    fun <T> split(list: List<T>): Array<List<*>> {
        // get size of the list
        val size = list.size
        // construct new list from the returned view by list.subList() method
        val first = ArrayList(list.subList(0, (size + 1) / 2))
        val second = ArrayList(list.subList((size + 1) / 2, size))

        // return an List array to accommodate both lists
        return arrayOf(first, second)
    }


    private fun <T> getRandomItems(
        totalPicksAtRandom: Int,
        list: List<T>
    ): ArrayList<T> {
        val pickedList: java.util.ArrayList<T> = arrayListOf() //
        var listOfEngineers: java.util.ArrayList<T> = arrayListOf() //
        var randomIndexNum = 0
        val random = Random()  // TODO Can be Injected but caution

        // TODO // Check list is not empty throws
        // TODO Check total is not more than the list throws
        // TODO Check if Set or List Mutable or Immutbale is better DataStrucutre.

        listOfEngineers.addAll(list)

        if (listOfEngineers.size > totalPicksAtRandom) {

            listOfEngineers.let { ofEngineers ->
                // Generate Rnadom Number based on items.

                // TODO To make it more unique and fair shuffle the given list before picking
                //  Timber.d("List before Reshuffle : $ofEngineers")
                ofEngineers.shuffle()
                // Loop through by total and assign random enginner to list
                for (i in 0 until totalPicksAtRandom) {
                    // Timber.d("List of Engin: $ofEngineers")
                    randomIndexNum = random.nextInt(ofEngineers.size)  // generate a random nmuber
                    //  Timber.d("$randomIndexNum Random Enginner: ${ofEngineers[randomIndexNum]}")
                    val engineer = ofEngineers[randomIndexNum]  // assign a random engineer
                    pickedList.add(engineer) // add item to picked list

                    /**
                     * Removes already picked item from list to avoid choosing same enginner
                     */
                    ofEngineers.removeAt(randomIndexNum) // since we are not itering through the list we can easily delete
                    ofEngineers.shuffle() // reshufles the list
                    //Timber.d("In Random After RemoveAt and Shuffle : $ofEngineers")
                }
                // During loop pop the chosen enginnerr to avoid same enginner wokring two shifts a day. and loop again.
                ///Timber.d("Random Number:  $randomIndexNum")
                //Timber.d("End")
            }

            // Timber.d("Picked:  $pickedList")

            return pickedList

        } else {

            //Timber.d("Resturned Coz Size is 1:  $listOfEngineers")
            return listOfEngineers
        }


//        /**
//         * Assign Shift
//         *
//         * Iterate through the list of picked Engneers
//         */
//        // iterate through a weekDays assiging enginners on each day
//        for (i in pickedList.indices) {
//            pickedList[i].shift = listOfShifts[i]
//        }


        //return pickedList
    }


    private fun getRandomItemFromList(
        list: List<Int>
    ): Int {
        var pickedItem: Int = -1
        val listOfEngineers: ArrayList<Int> = arrayListOf() //
        var randomIndexNum = 0
        val random = Random()  // TODO Can be Injected but caution
        listOfEngineers.addAll(list)

        //listOfEngineers.shuffle()
        randomIndexNum = random.nextInt(listOfEngineers.size)  // generate a random nmuber
        pickedItem = listOfEngineers[randomIndexNum]  // assign a random engineer

        Timber.d("Picked:  $pickedItem from list ${listOfEngineers}")

        return pickedItem

    }


}