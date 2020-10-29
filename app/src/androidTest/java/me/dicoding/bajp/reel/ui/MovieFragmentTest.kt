package me.dicoding.bajp.reel.ui

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.dicoding.bajp.reel.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFragmentTest{
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMovies(){
        SystemClock.sleep(1000)
        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19)
        )
    }

    @Test
    fun loadDetails(){
        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click())
        )

        SystemClock.sleep(1000)
        onView(withId(R.id.title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}