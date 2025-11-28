package com.example.vinyls

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.model.AlbumToCreate
import com.example.vinyls.ui.CreateAlbumScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateAlbumScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private var savedAlbum: AlbumToCreate? = null

    @Before
    fun setUp() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        savedAlbum = null

        composeTestRule.setContent {
            CreateAlbumScreen(
                navController = navController,

            )
        }
    }

    @Test
    fun addAlbumScreen_displaysTitle() {
        composeTestRule
            .onNodeWithText("Add Album")
            .assertIsDisplayed()
    }

    @Test
    fun addAlbumScreen_displaysBackButton() {
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
    }

    @Test
    fun addAlbumScreen_displaysUploadCoverSection() {
        composeTestRule
            .onNodeWithText("Cover Image")
            .assertIsDisplayed()

    }

    @Test
    fun addAlbumScreen_displaysAllInputFields() {
        // Album Title
        composeTestRule
            .onNodeWithText("Album Title")
            .assertIsDisplayed()

        // Artist
        composeTestRule
            .onNodeWithText("Description")
            .assertIsDisplayed()

        // Genre
        composeTestRule
            .onNodeWithText("Genre")
            .assertIsDisplayed()

        // Release Year
        composeTestRule
            .onNodeWithText("Release Year")
            .assertIsDisplayed()
    }

    @Test
    fun addAlbumScreen_displaysSaveButton() {
        composeTestRule
            .onNodeWithText("Save Album")
            .assertIsDisplayed()
    }

    @Test
    fun addAlbumScreen_saveButtonIsDisabledInitially() {
        composeTestRule
            .onNodeWithText("Save Album")
            .assertIsNotEnabled()
    }



}