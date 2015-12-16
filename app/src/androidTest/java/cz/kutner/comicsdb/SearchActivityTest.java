package cz.kutner.comicsdb;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Rule;
import org.junit.Test;

import cz.kutner.comicsdb.activity.MainActivity;



public class SearchActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

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
}
