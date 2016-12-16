package cz.kutner.comicsdb

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import cz.kutner.comicsdb.activity.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test


class SearchActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun search() {
        onView(withId(R.id.searchView)).perform(click()).perform(typeText("Batman"), pressKey(66))
        onView(allOf(withId(R.id.comics_name), withText("Batman #1"))).check(matches(withText("Batman #1")))
    }

    @Test
    fun searchSwipe() {
        onView(withId(R.id.searchView)).perform(click()).perform(typeText("Batman"), pressKey(66))
        onView(withId(R.id.pager)).perform(swipeLeft(), swipeLeft(), swipeRight(), swipeRight())
    }
}
