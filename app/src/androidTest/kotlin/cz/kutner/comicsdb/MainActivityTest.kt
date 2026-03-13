package cz.kutner.comicsdb

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import cz.kutner.comicsdb.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun openDrawer() {
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun comicsListLoads() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Menu").assertIsDisplayed()
    }

    @Test
    fun authorTest() {
        openDrawer()
        composeTestRule.onNodeWithText("Autoři").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun classifiedTest() {
        openDrawer()
        composeTestRule.onNodeWithText("Bazar").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun forumTest() {
        openDrawer()
        composeTestRule.onNodeWithText("Forum").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun newsTest() {
        openDrawer()
        composeTestRule.onNodeWithText("Novinky").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun seriesTest() {
        openDrawer()
        composeTestRule.onNodeWithText("Serie").performClick()
        composeTestRule.waitForIdle()
    }
}
