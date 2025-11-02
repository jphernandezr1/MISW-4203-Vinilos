package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.vinyls.ui.theme.VinylsTheme
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import androidx.compose.ui.test.onNodeWithText
import com.example.vinyls.ui.CollectorsListScreen
import androidx.compose.ui.test.onAllNodesWithText

@RunWith(AndroidJUnit4::class)
class CollectorsListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun collectorsFragment_displaysTitle() {
        // Given
        composeTestRule.setContent {
            VinylsTheme {
                CollectorsListScreen()
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Collectors")
            .assertIsDisplayed()

    }


    @Test
    fun collectorsFragment_displaysSearchBar() {
        // Given
        composeTestRule.setContent {
            VinylsTheme {
                CollectorsListScreen()
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Search for Collector")
            .assertIsDisplayed()
    }

    @Test
    fun collectorsFragment_displaysFilterChips() {
        // Given
        composeTestRule.setContent {
            VinylsTheme {
                CollectorsListScreen()
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Location")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Genres")
            .assertIsDisplayed()
    }

    @Test
    fun collectorsFragment_displaysCollectorsList() {
        // Given
        composeTestRule.setContent {
            VinylsTheme {
                CollectorsListScreen()
            }
        }

        // Wait for loading to finish
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Manolo Bellon")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Then - Verify all collectors are displayed
        composeTestRule
            .onNodeWithText("Manolo Bellon")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("manollo@caracol.com.co")
            .assertIsDisplayed()
    }
}