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


        fun performSwap(input: Array<Int>, index_A: Int, index_B: Int): Array<Int> {
            val inputCopy = input.copyOf()
            val temp = inputCopy[index_A]
            inputCopy[index_A] = inputCopy[index_B]
            inputCopy[index_B] = temp

            return inputCopy
        }


        /**
         * Filters the array to swap items that appear more than once in a row
         * To avoid double shifts.
         */
        fun filterArray(input: ArrayList<Array<Int>>) : ArrayList<Array<Int>>{

            val output : ArrayList<Array<Int>> = arrayListOf()

            val arrayTop = input[0]
            val arrayBottom = input[1]
            var arrayTopCopy = arrayTop.copyOf()


            for (x in arrayTop.indices) {

                if (arrayTop[x] == arrayBottom[x]) {
                    //Timber.d("Found Similar at [$x]")
                    arrayTopCopy = performSwap(arrayTopCopy, x, 1)
                }
            }

            output.add(arrayTopCopy)
            output.add(arrayBottom)

            return output

        }

        // Generic function to splitList a list into two sublists in Java
        fun <T> splitList(list: List<T>): Array<List<*>> {
            // get size of the list
            val size = list.size
            // construct new list from the returned view by list.subList() method
            val first = ArrayList(list.subList(0, (size + 1) / 2))
            val second = ArrayList(list.subList((size + 1) / 2, size))

            // return an List array to accommodate both lists
            return arrayOf(first, second)
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
    }


}

