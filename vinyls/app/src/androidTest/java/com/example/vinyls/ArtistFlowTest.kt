package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class ArtistFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun waitUntilArtistAppears(name: String, timeoutMillis: Long = 40_000) {
        composeTestRule.waitUntil(timeoutMillis) {
            try {
                composeTestRule.onNode(hasText(name), useUnmergedTree = true).assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }

    @Test
    fun navigateToArtistDetail_displaysBiography() {
        composeTestRule.onNode(hasText("Artists")).performClick()

        val knownArtist = "Rubén Blades Bellido de Luna"
        waitUntilArtistAppears(knownArtist)

        composeTestRule.onNode(hasText(knownArtist), useUnmergedTree = true).performClick()

        waitUntilArtistAppears("Biography")
        composeTestRule.onNodeWithText("Biography").assertIsDisplayed()
        composeTestRule.onNodeWithText("Discography").assertIsDisplayed()
    }
}
