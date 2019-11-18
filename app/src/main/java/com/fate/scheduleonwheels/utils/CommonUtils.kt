package com.fate.scheduleonwheels.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.IntentFilter
import android.net.Uri
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import com.fate.data.di.entities.Engineer
import com.fate.data.di.entities.Schedule
import com.fate.data.di.entities.Shift
import org.joda.time.DateTime
import org.joda.time.Days
import timber.log.Timber
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.round


class CommonUtils {

    companion object {

        /**
         * Since we assume Schedule will span two weeks and start on first work day end on last work day.
         * We can assign numbers for a speicifc day of weekDays or better yet use Calendar.DayOFWeek
         * Val since it will not change
         */
         val weekDays: ArrayList<Int> = arrayListOf(
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY
        )

         val listOfShifts: List<Shift> = listOf(Shift.DAY, Shift.NIGHT)
        /**
         * returns a Calendar Day name based on the dateId.
         */
        fun getDayName(dateId: Int): String {
            val dayName: HashMap<Int, String> = hashMapOf<Int, String>(

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


        // Generates a weekDays's Schedule
         fun generateSchedules(
            list: List<Engineer>,
            totalNumberOfWeeks: Int,
            totalEngineersToPickAtRandom: Int
        ): ArrayList<Schedule> {

            // https://www.geeksforgeeks.org/kotlin-collections/

            val weekScheduledList:  ArrayList<Schedule> = arrayListOf() //

            // We add a PLus one since we countring from 1 not to have Week 0
            for (x in 0 until totalNumberOfWeeks) {

                for (i in weekDays.indices) {

                    val chosenEngineersList : ArrayList<Engineer> = getRandomEngineers(
                        totalEngineersToPickAtRandom,
                        list
                    )

                   //  Timber.d("Chosen EngineersList : $chosenEngineersList")

                    // https://stackoverflow.com/questions/46846025/how-to-clone-or-copy-a-list-in-kotlin
                    val shiftList  = generateShifts(chosenEngineersList).toList()

                    // Timber.d("Day : ${CommonUtils.getDayName(weekDays[i])}")
                    // Timber.d("Chosen Shift : $shiftList")

                    val weekdaySchedule = Schedule(
                        weekDays[i],
                        "Week ${x.plus(1)}",
                        chosenEngineersList.toList(),
                        shiftList
                    )

                    weekScheduledList.add(weekdaySchedule)
                }

            }

            return weekScheduledList
        }

         fun generateShifts(list: List<Engineer>) : ArrayList<Shift>  {

            val shifts: ArrayList<Shift> = arrayListOf() //
            var shiftPosition = 0

            for (x in list.indices) {
                shifts.add(listOfShifts[shiftPosition])
                // checks to reset the shifPostion when it reaches 1
                if (shiftPosition <= 0) shiftPosition += 1 else shiftPosition = 0
            }
            return shifts
        }

        /**
         * given a List_shouldReturnARandomElement
         * total is the total ammount of enginners to select
         * list is the list to pick from
         */
        private fun getRandomEngineers(
            totalPicksAtRandom: Int,
            list: List<Engineer>
        ): ArrayList<Engineer> {
            val pickedList: ArrayList<Engineer> = arrayListOf() //
            var listOfEngineers: ArrayList<Engineer> = arrayListOf() //
            var randomIndexNum = 0
            val random = Random()  // TODO Can be Injected but caution


            //val listOfEngineers = list
            // TODO // Check list Int since Int is Unique enough throws
            // TODO // Check list is not empty throws
            // TODO Check total is not more than the list throws
            // TODO Check if Set or List Mutable or Immutbale is better DataStrucutre.

            listOfEngineers.addAll(list)

            listOfEngineers.let { ofEngineers ->
                // Generate Rnadom Number based on items.

                // TODO To make it more unique and fair shuffle the given list before picking
                //  Timber.d("List before Reshuffle : $ofEngineers")
                //ofEngineers.shuffle()
                // Loop through by total and assign random enginner to list
                for (i in 0 until totalPicksAtRandom) {
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

//        /**
//         * Assign Shift
//         *
//         * Iterate through the list of picked Engneers
//         */
//        // iterate through a weekDays assiging enginners on each day
//        for (i in pickedList.indices) {
//            pickedList[i].shift = listOfShifts[i]
//        }


            return pickedList
        }



    }


}

