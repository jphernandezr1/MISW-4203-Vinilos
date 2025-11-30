package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class AddTracksFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addTrack_formSubmissionReturnsWithNewTrack() {
        val albumName = "Buscando América"
        val trackName = "UITest Track " + System.currentTimeMillis()
        val trackNumber = (System.currentTimeMillis() % 9000 + 1000).toString()

        waitUntilAlbumAppears(albumName)

        composeTestRule
            .onNode(hasTestTag("albums_grid"))
            .performScrollToNode(hasText(albumName))

        composeTestRule
            .onNode(hasText(albumName), useUnmergedTree = true)
            .performClick()

        waitUntilNodeWithTag("album_detail_screen")
        assertActionButtonVisible("album_detail_add_tracks_button")

        composeTestRule
            .onNode(hasTestTag("album_detail_add_tracks_button"))
            .performClick()

        waitUntilNodeWithTag("add_tracks_submit_button")

        composeTestRule
            .onNodeWithTag("add_tracks_title_input_1", useUnmergedTree = true)
            .performTextInput(trackName)
        composeTestRule
            .onNodeWithTag("add_tracks_duration_input_1", useUnmergedTree = true)
            .performTextInput("4:00")
        composeTestRule
            .onNodeWithTag("add_tracks_number_input_1", useUnmergedTree = true)
            .performTextInput(trackNumber)

        composeTestRule
            .onNode(hasTestTag("add_tracks_submit_button"))
            .performClick()

        waitUntilNodeWithTag("album_detail_screen")

        waitUntilTextAppears(trackName)

        assertTrackVisible(trackName)
    }

    private fun waitUntilAlbumAppears(title: String, timeoutMillis: Long = 60_000) {
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

    private fun waitUntilNodeWithTag(tag: String, timeoutMillis: Long = 60_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            composeTestRule
                .onAllNodesWithTag(tag, useUnmergedTree = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
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

    private fun assertTrackVisible(trackName: String, maxScrolls: Int = 8) {
        repeat(maxScrolls) {
            try {
                composeTestRule
                    .onNode(hasText(trackName), useUnmergedTree = true)
                    .assertIsDisplayed()
                return
            } catch (_: AssertionError) {
                composeTestRule
                    .onNode(hasTestTag("album_detail_screen"))
                    .performTouchInput { swipeUp() }
            }
        }
        composeTestRule
            .onNode(hasText(trackName), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    private fun waitUntilTextAppears(text: String, timeoutMillis: Long = 60_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            composeTestRule
                .onAllNodesWithText(text, useUnmergedTree = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }
}
