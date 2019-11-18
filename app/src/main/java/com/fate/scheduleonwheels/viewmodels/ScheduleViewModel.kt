package com.fate.scheduleonwheels.viewmodels

import androidx.lifecycle.MutableLiveData
import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.data.di.entities.WeekDay
import com.fate.scheduleonwheels.base.BaseViewModel
import com.fate.scheduleonwheels.utils.CommonUtils
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.filterArray
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.generateShifts
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.getRandomItemFromList
import com.fate.scheduleonwheels.utils.CommonUtils.Companion.splitList
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class ScheduleViewModel() : BaseViewModel() {

    private var dataSchedule: MutableLiveData<ArrayList<Schedule>> = MutableLiveData()

    fun getDataSchedule(): MutableLiveData<ArrayList<Schedule>> {
        return dataSchedule
    }

    fun generateSchedule(list: List<Engineer>) {

        val weekScheduledList: java.util.ArrayList<Schedule> = arrayListOf() //

        val hashMapEngineers: HashMap<Int, Engineer> = hashMapOf()
        // Create Weight HasMap
        for (i in list.indices) {
            hashMapEngineers[list[i].id] = list[i]
        }

        //Timber.d("hashMapEngineers: $hashMapEngineers")

        val listOfEngineerIds = ArrayList(hashMapEngineers.keys)

        val generatedWeekdays = generateWeekDays(listOfEngineerIds)

        // map Engineers based on the ids to generate a Schedule
        for (i in generatedWeekdays.indices) {

            val chosenList = mutableListOf<Engineer>()
            val chosenIds = generatedWeekdays[i].ids

            // loop through ids mapping with Engineer HashMap
            for (j in chosenIds.indices) {
                chosenList.add(hashMapEngineers[chosenIds[j]]!!)
            }

            val shiftList = generateShifts(chosenList).toList()

            val weekdaySchedule = Schedule(
                generatedWeekdays[i].date,
                generatedWeekdays[i].week,
                chosenList.toList(),
                shiftList
            )

            weekScheduledList.add(weekdaySchedule)

        }

        dataSchedule.value = weekScheduledList

    }


    private fun generateWeekDays(
        list: MutableList<Int>
    ): ArrayList<WeekDay> {

        val weekdaysList: ArrayList<WeekDay> = arrayListOf()
        val shiftsArray = arrayListOf<Array<Int>>()  // multidimensional array
        var filteredshiftsArray : ArrayList<Array<Int>> = arrayListOf<Array<Int>>()

        // we want the array be of size 2 by 5
        for (i in 0 until 2) {
            var array = arrayOf<Int>()
            for (j in 0..4) {
                array += 0  // assings zero as intial value
            }
            shiftsArray += array
        }

        // splitList list into two
        val splitList = splitList(list)

        // Based on two weeks
        for (x in 0 until 2) {

            var previuosSelection: Int = -1

            /// Assign Group
            val group = splitList[x] as List<Int>

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

                    if (previuosSelection != -1 && listCopy.size > 1) {
                        // remove arleady chosen Items to Obey Rule 3
                        listCopy.removeIf {
                            it == previuosSelection
                        }
                    }

                    val random = getRandomItemFromList(listCopy)
                    previuosSelection = random
                    // Recording Weights to the HashMap
                    hashMapOfTotalShiftsEach[previuosSelection] =
                        hashMapOfTotalShiftsEach[previuosSelection]!!.plus(1)
                    shiftsArray[j][i] = random
                }
            }


           // Timber.d("hashMapOfTotalShiftsEach: $hashMapOfTotalShiftsEach")

            filteredshiftsArray =  filterArray(shiftsArray)  // To remove Double Shifts

            // Now we Iterate through the days of the week Mon to Fri Assinging Weekkdays
            for (i in CommonUtils.weekDays.indices) {

                val a = filteredshiftsArray[0][i]
                val b = filteredshiftsArray[1][i]
                weekdaysList.add(WeekDay(CommonUtils.weekDays[i],"Week ${x.plus(1)}",listOf(a,b)))

            }

        }

        return weekdaysList

    }





}