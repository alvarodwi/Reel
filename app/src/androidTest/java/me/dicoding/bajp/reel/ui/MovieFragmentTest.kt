package me.dicoding.bajp.reel.ui

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFragmentTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idleResource)
    }

    @After
    fun after(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idleResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19)
        )
    }

    @Test
    fun loadDetails() {
        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        //akomodasi waktu perpindahan dari list ke detail
        SystemClock.sleep(1000)
        onView(withId(R.id.backdrop)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}