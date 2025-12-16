package cz.kutner.comicsdb

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import cz.kutner.comicsdb.main.MainActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun comicsListandDetail() {
        onView(
            allOf(
                withId(R.id.recycler_view),
                hasDescendant(withId(R.id.card_view_comics))
            )
        ).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.name)).check(matches(notNullValue()))
        onView(withId(R.id.cover)).perform(click())
        onView(withId(R.id.image_pager)).check(matches(notNullValue()))
    }

    @Test
    fun authorTest() {
        val imageButton = onView(
            allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                ViewMatchers.isDisplayed()
            )
        )
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
            allOf(
                ViewMatchers.withText("Autoři"),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun classifiedTest() {
        val imageButton = onView(
            allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                ViewMatchers.isDisplayed()
            )
        )
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
            allOf(
                ViewMatchers.withText("Bazar"),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun forumTest() {
        val imageButton = onView(
            allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                ViewMatchers.isDisplayed()
            )
        )
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
            allOf(
                ViewMatchers.withText("Forum"),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun newsTest() {
        val imageButton = onView(
            allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                ViewMatchers.isDisplayed()
            )
        )
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
            allOf(
                ViewMatchers.withText("Novinky"),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun search() {
        onView(withId(R.id.searchView)).perform(click())
            .perform(ViewActions.typeText("Batman #01"), ViewActions.pressKey(66))
        onView(allOf(withId(R.id.comics_name), ViewMatchers.withText("Batman #01"))).check(
            matches(
                ViewMatchers.withText("Batman #01")
            )
        )
    }

    @Test
    fun searchSwipe() {
        onView(withId(R.id.searchView)).perform(click())
            .perform(ViewActions.typeText("Batman"), ViewActions.pressKey(66))
        onView(withId(R.id.pager)).perform(
            ViewActions.swipeLeft(),
            ViewActions.swipeLeft(),
            ViewActions.swipeRight(),
            ViewActions.swipeRight()
        )
    }

    @Test
    fun seriesTest() {
        val imageButton = onView(
            allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                ViewMatchers.isDisplayed()
            )
        )
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
            allOf(
                ViewMatchers.withText("Serie"),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatCheckedTextView.perform(click())
    }
}
