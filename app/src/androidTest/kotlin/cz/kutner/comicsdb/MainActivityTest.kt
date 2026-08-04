package cz.kutner.comicsdb

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
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

    @Test
    fun backFromDetailRestoresMainScreen() {
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 15_000) {
            composeTestRule.onAllNodes(
                hasClickAction() and hasAnyAncestor(hasScrollAction())
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodes(
            hasClickAction() and hasAnyAncestor(hasScrollAction())
        )[0].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodes(
                androidx.compose.ui.test.hasContentDescription("Zpět")
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.onNodeWithContentDescription("Zpět").performClick()
        composeTestRule.mainClock.advanceTimeBy(16)
        composeTestRule.onAllNodes(
            androidx.compose.ui.test.hasContentDescription("Zpět")
        ).onFirst().performClick()
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Menu").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Novinky").fetchSemanticsNodes().let {
            assert(it.isNotEmpty()) { "Drawer not visible — white screen reproduced" }
        }
    }
}
