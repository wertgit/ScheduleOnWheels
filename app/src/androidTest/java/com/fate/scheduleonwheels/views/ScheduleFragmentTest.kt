package com.fate.scheduleonwheels.views

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.fate.scheduleonwheels.R
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
@LargeTest
class ScheduleFragmentTest{


    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToScheduleFragment(){
        activityTestRule.activity.apply {
            runOnUiThread {
                displayScheduleScreen()
            }
        }
    }

    @Test
    fun testScheduleScreenIsDisplayed() {
        onView(withId(R.id.recycler_schedule_list)).check(matches(isDisplayed()))
    }


    @Test
    fun testNavigateToEngineersScreenOnBackButtonClicked() {
        Espresso.pressBack()
        onView(withId(R.id.recycler_engineers_list)).check(matches(isDisplayed()))
    }



}