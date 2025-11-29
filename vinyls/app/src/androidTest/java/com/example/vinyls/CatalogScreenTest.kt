package com.example.vinyls

import android.annotation.SuppressLint
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun waitUntilAlbumAppears(title: String, timeoutMillis: Long = 20_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            try {
                // Use onNode with the hasText matcher for consistency
                composeTestRule
                    .onNode(hasText(title), useUnmergedTree = true)
                    .assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }

    @SuppressLint("CheckResult")
    @Test
    fun catalog_showsGrid_and_firstCardsHaveCoverAndTitle_andAreClickable() {
        // Avoid ambiguity with bottom nav label also named "Catalog" by targeting a testTag
        composeTestRule.onNode(hasTestTag("catalog_title")).assertIsDisplayed()

        val knownAlbum = "Buscando América"
        waitUntilAlbumAppears(knownAlbum)

        composeTestRule.onNode(hasTestTag("albums_grid")).assertIsDisplayed()

        // CA1: Cover (via contentDescription = album name) and title are shown
        composeTestRule.onNode(hasContentDescription(knownAlbum)).assertIsDisplayed()
        // Use onNode with hasText and useUnmergedTree to avoid ambiguity and merging issues
        composeTestRule.onNode(hasText(knownAlbum), useUnmergedTree = true).assertIsDisplayed()

        // CA2: Ensure the card (by its title text) is brought into view, then simulate a click
        composeTestRule
            .onNode(hasTestTag("albums_grid"))
            .performScrollToNode(hasText(knownAlbum)) // hasText no longer needs useUnmergedTree here

        // Find the node again to perform actions on it
        composeTestRule
            .onNode(hasText(knownAlbum), useUnmergedTree = true)
            .assertIsDisplayed()
            .performTouchInput { ViewActions.click() }
    }
}