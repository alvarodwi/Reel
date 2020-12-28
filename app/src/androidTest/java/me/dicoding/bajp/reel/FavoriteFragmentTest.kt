package me.dicoding.bajp.reel

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.dicoding.bajp.reel.core.utils.EspressoIdlingResource
import me.dicoding.bajp.reel.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {
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
  fun favoriteTest() {
    val randomPos = Random.nextInt(0, 19)
    //randomly favorite an items (hopefully that item isn't added to favorite yet!) and then go back to homepage
    //this is to assure that favorite list has items so the list can be displayed
    onView(ViewMatchers.withId(R.id.rv_list)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(randomPos, click())
    )
    onView(ViewMatchers.withId(R.id.fab_favorite)).perform(click())
    onView(isRoot()).perform(ViewActions.pressBack())

    // go to favorite, check list displayed and then click the first item
    onView(ViewMatchers.withId(R.id.action_favorite)).perform(click())
    onView(ViewMatchers.withId(R.id.rv_list)).check(
      ViewAssertions.matches(ViewMatchers.isDisplayed())
    )
    onView(ViewMatchers.withId(R.id.rv_list)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
    )

    // on new page (detail page, check if it's displayed, and then go back double time to start at homepage
    onView(ViewMatchers.withId(R.id.backdrop)).check(
      ViewAssertions.matches(ViewMatchers.isDisplayed())
    )
    onView(isRoot()).perform(ViewActions.pressBack())
    onView(isRoot()).perform(ViewActions.pressBack())

    // de-favorite the item...
    onView(ViewMatchers.withId(R.id.rv_list)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(randomPos, click())
    )
    onView(ViewMatchers.withId(R.id.fab_favorite)).perform(click())
  }
}