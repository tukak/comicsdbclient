package cz.kutner.comicsdb

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit
import org.koin.test.KoinTest
import android.view.WindowManager
import cz.kutner.comicsdb.main.MainActivity



@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)
    private val client by inject<OkHttpClient>()
    private val idlingResource by lazy { OkHttp3IdlingResource.create("okhttp", client)}

    @Before
    fun registerIdlingResource() {
        IdlingPolicies.setIdlingResourceTimeout(120, TimeUnit.MINUTES)
        IdlingPolicies.setMasterPolicyTimeout(120, TimeUnit.MINUTES)
        Espresso.registerIdlingResources(idlingResource)
        //IdlingRegistry.getInstance().register(idlingResource)
    }

    @Before
    fun setUp() {
        val activity = activityRule.activity
        val wakeUpDevice = Runnable {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
    }

    @Test
    fun comicsListandDetail() {

        onView(allOf(withId(R.id.recycler_view), hasDescendant(withId(R.id.card_view_comics)))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.name)).check(matches(notNullValue()))
        onView(withId(R.id.cover)).perform(click())
        onView(withId(R.id.image_pager)).check(matches(notNullValue()))
    }

    @Test
    fun authorTest() {
        val imageButton = onView(
                allOf(ViewMatchers.withContentDescription("Navigate up"),
                        ViewMatchers.withParent(allOf(withId(R.id.toolbar),
                                ViewMatchers.withParent(withId(R.id.appbar)))),
                        ViewMatchers.isDisplayed()))
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), ViewMatchers.withText("Autoři"), ViewMatchers.isDisplayed()))
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun classifiedTest() {
        val imageButton = onView(
                allOf(ViewMatchers.withContentDescription("Navigate up"),
                        ViewMatchers.withParent(allOf(withId(R.id.toolbar),
                                ViewMatchers.withParent(withId(R.id.appbar)))),
                        ViewMatchers.isDisplayed()))
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), ViewMatchers.withText("Bazar"), ViewMatchers.isDisplayed()))
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun forumTest() {
        val imageButton = onView(
                allOf(ViewMatchers.withContentDescription("Navigate up"),
                        ViewMatchers.withParent(allOf(withId(R.id.toolbar),
                                ViewMatchers.withParent(withId(R.id.appbar)))),
                        ViewMatchers.isDisplayed()))
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), ViewMatchers.withText("Forum"), ViewMatchers.isDisplayed()))
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun newsTest() {
        val imageButton = onView(
                allOf(ViewMatchers.withContentDescription("Navigate up"),
                        ViewMatchers.withParent(allOf(withId(R.id.toolbar),
                                ViewMatchers.withParent(withId(R.id.appbar)))),
                        ViewMatchers.isDisplayed()))
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), ViewMatchers.withText("Novinky"), ViewMatchers.isDisplayed()))
        appCompatCheckedTextView.perform(click())
    }

    @Test
    fun search() {
        onView(withId(R.id.searchView)).perform(click()).perform(ViewActions.typeText("Batman #01"), ViewActions.pressKey(66))
        onView(allOf(withId(R.id.comics_name), ViewMatchers.withText("Batman #01"))).check(matches(ViewMatchers.withText("Batman #01")))
    }

    @Test
    fun searchSwipe() {
        onView(withId(R.id.searchView)).perform(click()).perform(ViewActions.typeText("Batman"), ViewActions.pressKey(66))
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft(), ViewActions.swipeLeft(), ViewActions.swipeRight(), ViewActions.swipeRight())
    }

    @Test
    fun seriesTest() {
        val imageButton = onView(
                allOf(ViewMatchers.withContentDescription("Navigate up"),
                        ViewMatchers.withParent(allOf(withId(R.id.toolbar),
                                ViewMatchers.withParent(withId(R.id.appbar)))),
                        ViewMatchers.isDisplayed()))
        imageButton.perform(click())

        val appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), ViewMatchers.withText("Serie"), ViewMatchers.isDisplayed()))
        appCompatCheckedTextView.perform(click())
    }

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource)
        //IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
