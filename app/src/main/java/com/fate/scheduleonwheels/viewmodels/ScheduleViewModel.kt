package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.data.di.entities.Shift
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.utils.CommonUtils
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.generateSchedules
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.Arrays.asList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.icu.lang.UCharacter.GraphemeClusterBreak.T








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

                    if(list[j].id == generatedSchdule[i][k]){
                        chosenEngineersList.add(list[j])
                    }

                }

            }

            Timber.d("Chosen Enginners: $chosenEngineersList")

            val shiftList  = CommonUtils.generateShifts(chosenEngineersList).toList()

            val weekdaySchedule = Schedule(
                4,
                "Week ${1.plus(1)}",
                chosenEngineersList.toList(),
                shiftList
            )

            weekScheduledList.add(weekdaySchedule)

            Timber.d("Chosen WeekdaySchedule: $weekdaySchedule")
        }

        Timber.d("The Week: $generatedSchdule")

        //dataSchedule.value = generateSchedules(list, totalNumberOfWeeks,totalEngineersToPickAtRandom)

        dataSchedule.value = weekScheduledList
    }

    fun splitEngineersIntoGroups(list: MutableList<Int>) : ArrayList<List<Int>>{

        val weekScheduledList: ArrayList<List<Int>> = arrayListOf()

        Timber.d("Lisr of Ids: $list")

        //val engineersRawList = mutableListOf(0,1,2,3,4,5,6,7,8,9)
        val engineersRawList = list

        // split list into two.
        val splitList = split(engineersRawList)


        for (x in 0 until totalNumberOfWeeks) {

            var previuosChosen : List<Int> = listOf()
            var hashMapOfTotalShiftsEach : HashMap<Int, Int> = hashMapOf()

            /// Assign Group A to First Week
            val groupA = splitList[x] as List<Int>


            //Timber.d("groupA: $groupA")

            // Create
            for (i in groupA.indices) {
                hashMapOfTotalShiftsEach[groupA[i]] = 0
            }


             //Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

            // Now we Iterate through the days of the week Mon to Fri Assinging Two people

            for (i in CommonUtils.weekDays.indices) {

                //Timber.d("previuosChosen$i: $previuosChosen")
                val groupACopy = groupA.toMutableList()  // make a copy of the group list

                if(!hashMapOfTotalShiftsEach.isNullOrEmpty() && groupACopy.size >totalEngineersToPickAtRandom){
                    // remove arleady chosen Items to Obey Rule 3

                    groupACopy.removeIf {
                        hashMapOfTotalShiftsEach[it]!! > 1
                    }

                }

                if(!previuosChosen.isNullOrEmpty() && groupACopy.size > totalEngineersToPickAtRandom){
                    // remove arleady chosen Items to Obey Rule 3

                    groupACopy.removeIf {
                        it == previuosChosen[0] || it == previuosChosen[1]
                    }
                    // Timber.d("groupACopy After Remove: $groupACopy")
                }

                //Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")
                //Timber.d("groupACopy After Remove: $groupACopy")

                val randomChosen = getRandomItems(totalEngineersToPickAtRandom, groupACopy)
                previuosChosen = randomChosen

                // Adding Shift Weights to the HashMap
                for(item in randomChosen){
                    hashMapOfTotalShiftsEach[item] = hashMapOfTotalShiftsEach.get(item)!!.plus(1)
                }

                weekScheduledList.add(randomChosen)
                //Timber.d("randomChosen$i: $randomChosen")



            }


           Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

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


        //val listOfEngineers = list
        // TODO // Check list Int since Int is Unique enough throws
        // TODO // Check list is not empty throws
        // TODO Check total is not more than the list throws
        // TODO Check if Set or List Mutable or Immutbale is better DataStrucutre.

        listOfEngineers.addAll(list)

        if(listOfEngineers.size > totalPicksAtRandom){

            listOfEngineers.let { ofEngineers ->
                // Generate Rnadom Number based on items.

                // TODO To make it more unique and fair shuffle the given list before picking
                //  Timber.d("List before Reshuffle : $ofEngineers")
                //ofEngineers.shuffle()
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
                    // Timber.d("List after Remove and ReShuffle : $ofEngineers")
                }
                // During loop pop the chosen enginnerr to avoid same enginner wokring two shifts a day. and loop again.
                ///Timber.d("Random Number:  $randomIndexNum")
                //Timber.d("End")
            }

            return pickedList

        } else return listOfEngineers



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


}