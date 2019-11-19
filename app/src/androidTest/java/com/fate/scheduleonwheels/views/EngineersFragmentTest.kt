package com.fate.scheduleonwheels.views

import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.fate.data.data.entities.Engineer
import com.fate.scheduleonwheels.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


@RunWith(AndroidJUnit4::class)
@LargeTest
class EngineersFragmentTest{

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToEngineersFragment(){
        activityTestRule.activity.apply {
            runOnUiThread {
                displayEngineersScreen()
            }
        }
    }

    @Test
    fun testEngineerScreenIsDisplayed() {
        onView(withId(R.id.recycler_engineers_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testEngineerScreenListHasAnItem() {
        Thread.sleep(3000)
        onView(nthChildOf(withId(R.id.recycler_engineers_list), 0)).check(matches(hasDescendant(withText("Bogdan"))))
    }

    @Test
    fun testGenerateButtonIsDisplayed() {
        onView(withId(R.id.button_generate_schedule)).check(matches(isDisplayed()))
    }

    @Test
    fun testGenerateButtonIsClickable() {
        onView(withId(R.id.button_generate_schedule)).check(matches(isClickable()))
    }

    @Test
    fun testNavigateToScheduleScreen() {
        onView(withId(R.id.button_generate_schedule)).perform(click())
        onView(withId(R.id.recycler_schedule_list)).check(matches(isDisplayed()))
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.getParent() !is ViewGroup) {
                    return parentMatcher.matches(view.getParent())
                }

                val group = view.getParent() as ViewGroup
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition) == view
            }
        }
    }

}