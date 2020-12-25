package me.dicoding.bajp.reel.core

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.core.internal.deps.guava.base.Predicate
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

//menghindari AmbiguousViewMatcherException espresso
//https://alteral.github.io/note-18/
object ViewCounter {
  fun first(matcher: Matcher<View>): Matcher<View?>? {
    return object : BaseMatcher<View>() {
      var isFirst = true
      override fun matches(item: Any): Boolean {
        if (isFirst && matcher.matches(item)) {
          isFirst = false
          return true
        }
        return false
      }

      override fun describeTo(description: Description) {
        description.appendText("should return first matching item")
      }
    }
  }

  fun last(matcher: Matcher<View>): Matcher<View?>? {
    return getElementFromMatchAtPosition(matcher, getCount(matcher) - 1)
  }

  private fun getElementFromMatchAtPosition(
    matcher: Matcher<View>,
    position: Int
  ): Matcher<View?>? {
    return object : BaseMatcher<View?>() {
      var counter = 0

      override fun matches(item: Any): Boolean =
        matcher.matches(item) && counter++ == position

      override fun describeTo(description: Description) {
        description.appendText("Element at hierarchy position $position")
      }
    }
  }

  private fun getCount(
    viewMatcher: Matcher<View>,
    countLimit: Int = 5
  ): Int {
    var actualViewsCount = 0
    do {
      try {
        Espresso.onView(ViewMatchers.isRoot())
          .check(ViewAssertions.matches(withViewCount(viewMatcher, actualViewsCount)))
        return actualViewsCount
      } catch (ignored: Error) {
      }
      actualViewsCount++
    } while (actualViewsCount < countLimit)
    throw Exception("Counting $viewMatcher was failed. Count limit exceeded")
  }

  private fun withViewCount(
    viewMatcher: Matcher<View>,
    expectedCount: Int
  ): Matcher<View?>? {
    return object : TypeSafeMatcher<View?>() {
      var actualCount = -1
      override fun describeTo(description: Description) {
        if (actualCount >= 0) {
          description.appendText("With expected number of items: $expectedCount")
          description.appendText("\n With matcher: ")
          viewMatcher.describeTo(description)
          description.appendText("\n But got: $actualCount")
        }
      }

      override fun matchesSafely(root: View?): Boolean {
        actualCount = 0
        val iterable = TreeIterables.breadthFirstViewTraversal(root)
        actualCount = Iterables.filter(iterable, withMatcherPredicate(viewMatcher)).count()
        return actualCount == expectedCount
      }
    }
  }

  private fun withMatcherPredicate(matcher: Matcher<View>): Predicate<View?>? {
    return Predicate<View?> { view -> matcher.matches(view) }
  }
}
