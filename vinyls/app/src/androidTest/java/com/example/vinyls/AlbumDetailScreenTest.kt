package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.test.performScrollToNode
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class AlbumDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun waitUntilAlbumAppears(title: String, timeoutMillis: Long = 40_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            try {
                composeTestRule
                    .onNode(hasText(title), useUnmergedTree = true)
                    .assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }

    private fun waitUntilNodeWithTag(tag: String, timeoutMillis: Long = 40_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            composeTestRule
                .onAllNodesWithTag(testTag = tag, useUnmergedTree = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun tappingAlbum_opensDetailAndShowsCoreSections() {
        val knownAlbum = "Buscando América"
        waitUntilAlbumAppears(knownAlbum)

        composeTestRule
            .onNode(hasTestTag("albums_grid"))
            .performScrollToNode(hasText(knownAlbum))

        composeTestRule
            .onNode(hasText(knownAlbum), useUnmergedTree = true)
            .performClick()

        waitUntilNodeWithTag("album_detail_screen")

        composeTestRule
            .onNode(hasText(knownAlbum), useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNode(hasTestTag("album_detail_tracklist_title"))
            .assertIsDisplayed()

        assertActionButtonVisible("album_detail_add_tracks_button")
        composeTestRule
            .onNode(hasTestTag("album_detail_add_tracks_button"))
            .assertIsDisplayed()
        assertActionButtonVisible("album_detail_add_collection_button")
        composeTestRule
            .onNode(hasTestTag("album_detail_add_collection_button"))
            .assertIsDisplayed()
        assertActionButtonVisible("album_detail_add_wishlist_button")
        composeTestRule
            .onNode(hasTestTag("album_detail_add_wishlist_button"))
            .assertIsDisplayed()
    }

    @Test
    fun addTracksButton_navigatesToForm() {
        val knownAlbum = "Buscando América"
        waitUntilAlbumAppears(knownAlbum)

        composeTestRule
            .onNode(hasTestTag("albums_grid"))
            .performScrollToNode(hasText(knownAlbum))

        composeTestRule
            .onNode(hasText(knownAlbum), useUnmergedTree = true)
            .performClick()

        waitUntilNodeWithTag("album_detail_screen")
        assertActionButtonVisible("album_detail_add_tracks_button")

        composeTestRule
            .onNode(hasTestTag("album_detail_add_tracks_button"))
            .performClick()

        composeTestRule.waitUntil(40_000) {
            composeTestRule
                .onAllNodesWithTag("add_tracks_submit_button", useUnmergedTree = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNode(hasTestTag("add_tracks_submit_button"))
            .assertIsDisplayed()
    }

    private fun assertActionButtonVisible(tag: String, maxScrolls: Int = 6) {
        repeat(maxScrolls) {
            try {
                composeTestRule
                    .onNode(hasTestTag(tag))
                    .assertIsDisplayed()
                return
            } catch (_: AssertionError) {
                composeTestRule
                    .onNode(hasTestTag("album_detail_screen"))
                    .performTouchInput { swipeUp() }
            }
        }
        composeTestRule
            .onNode(hasTestTag(tag))
            .assertIsDisplayed()
    }
}
