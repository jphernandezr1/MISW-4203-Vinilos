package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.ui.CollectorDetailFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        // Set the graph before using it
        composeTestRule.setContent {
            CollectorDetailFragment(
                navController = null,
                collectorId = 100
            )
        }
    }

    @Test
    fun collectorDetailScreen_displaysCorrectTitle() {
        // Verify the title is displayed
        composeTestRule
            .onNodeWithText("Collector Detail Screen")
            .assertIsDisplayed()
    }
    @Test
    fun collectorDetailScreen_displaysMusicalTasteTags() {
        composeTestRule
            .onNodeWithText("Indie Rock")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Electronic")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Hip Hop")
            .assertIsDisplayed()
    }


    @Test
    fun collectorDetailScreen_displaysFavoritePerformers() {
        composeTestRule
            .onNodeWithText("Favorite Performers")
            .assertIsDisplayed()


    }

    @Test
    fun collectorDetailScreen_scrollsToActivitySection() {
        // Scroll to find Activity section
        composeTestRule
            .onNodeWithText("Collector Albums")
            .performScrollTo()
            .assertIsDisplayed()
    }

}