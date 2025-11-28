package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.ui.ArtistListScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistsListScreenTest {

        @get:Rule
        val composeTestRule = createComposeRule()


        @Before
        fun setUp() {
            // Set the graph before using it
            composeTestRule.setContent {
                val navController = rememberNavController()
                ArtistListScreen(navController = navController)
            }
        }

        @Test
        fun artistsScreen_displaysCorrectTitle() {
            // Verify the title is displayed
            composeTestRule
                .onNodeWithText("Artists")
                .assertIsDisplayed()
        }
        @Test
        fun artistsScreen_showsSearchField() {
            composeTestRule
                .onNodeWithText("Search artists")
                .assertIsDisplayed()
        }


        @Test
        fun artistsScreen_displaysAnArtist() {
            // Wait for loading to finish
            composeTestRule.waitUntil(timeoutMillis = 5000) {
                composeTestRule
                    .onAllNodesWithText("Rubén Blades Bellido de Luna")
                    .fetchSemanticsNodes().isNotEmpty()
            }



        }




}