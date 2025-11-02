package com.example.vinyls

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


@RunWith(AndroidJUnit4::class)
class CollectorsListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    @get:Rule
//    val mActivityTestRule: ActivityScenarioRule<MainActivity?> =
//        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        composeTestRule.setContent {
            VinylsTheme {
                CollectorsListViewTest()
            }
        }

    }
}