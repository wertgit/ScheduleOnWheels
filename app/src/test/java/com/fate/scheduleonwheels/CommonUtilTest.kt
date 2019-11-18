package com.fate.scheduleonwheels

import com.fate.scheduleonwheels.utils.CommonUtils
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
}
