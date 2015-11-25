package cz.kutner.comicsdb;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.kutner.comicsdb.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void comicsListandDetail() {
        onView(allOf(withId(R.id.recycler_view), hasDescendant(withId(R.id.card_view_comics)))).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.name)).check(matches(notNullValue()));
    }

    @Test
    public void search(){
        onView(withId(R.id.searchView)).perform(click()).perform(typeText("Batman"), pressKey(66));
        onView(allOf(withId(R.id.comics_name), withText("Batman #1"))).check(matches(withText("Batman #1")));
    }

    @Test
    public void searchSwipe(){
        onView(withId(R.id.searchView)).perform(click()).perform(typeText("Batman"), pressKey(66));
        onView(withId(R.id.pager)).perform(swipeLeft(), swipeLeft(), swipeRight(), swipeRight());
    }

    @Test
    public void news() {
        onView(isRoot()).perform(swipeRight());
    }
}
