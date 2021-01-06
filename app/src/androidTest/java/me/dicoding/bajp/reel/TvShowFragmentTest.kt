package me.dicoding.bajp.reel

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.dicoding.bajp.reel.R.id
import me.dicoding.bajp.reel.core.ViewCounter
import me.dicoding.bajp.reel.core.utils.EspressoIdlingResource
import me.dicoding.bajp.reel.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowFragmentTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idleResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idleResource)
    }

    @Test
    fun loadTvShows() {
        onView(withId(id.view_pager)).perform(swipeLeft())
        onView(ViewCounter.last(withId(id.rv_list))).perform(
            RecyclerViewActions.scrollToPosition<ViewHolder>(19)
        )
    }

    @Test
    fun loadDetails() {
        onView(withId(id.view_pager)).perform(swipeLeft())
        // akomodasi waktu swipe
        // ini biang error ketika testing =(
        SystemClock.sleep(1000)
        onView(ViewCounter.last(withId(id.rv_list))).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click())
        )
        onView(withId(id.backdrop)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
