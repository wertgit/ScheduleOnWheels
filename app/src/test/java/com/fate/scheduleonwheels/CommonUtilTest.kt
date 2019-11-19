package com.fate.scheduleonwheels

import com.fate.data.data.entities.Engineer
import com.fate.scheduleonwheels.utils.CommonUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CommonUtilTest {

    @Test
    fun monday_isCorrect() {
        assertEquals("Monday", CommonUtils.getDayName(2))
    }

    @Test
    fun testGeneratedSchedulesReturnsItems() {
        val testEngineersList =
            listOf(
                Engineer(0, "Bogdan"),
                Engineer(1, "Nic"),
                Engineer(2, "Tung"),
                Engineer(3, "Gautam"),
                Engineer(4, "Bala"),
                Engineer(5, "Nazih"),
                Engineer(6, "Huteri"),
                Engineer(7, "Aldy"),
                Engineer(8, "Ankur"),
                Engineer(9, "Chinh")

            )

        val result = CommonUtils.generateSchedule(testEngineersList)
        assertThat(result.isNotEmpty(), `is`(true))
    }

    @Test
    fun testWeekDaysAreFive() {
        val value = CommonUtils.weekDays
        assertThat(value.size, `is`(5))
    }

    @Test
    fun testTwoShiftsAreGenerated() {
        val value = CommonUtils.generateShifts(2)
        assertThat(value.size, `is`(2))
    }

}
