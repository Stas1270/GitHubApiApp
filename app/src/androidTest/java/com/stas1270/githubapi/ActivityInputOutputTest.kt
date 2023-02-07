package com.stas1270.githubapi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ActivityInputOutputTest {

    @get:Rule
    val activity = ActivityScenarioRule(FirstActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.stas1270.githubapi", appContext.packageName)
    }

    @Test
    fun activityLaunch() {
        onView(withId(R.id.button_main)).perform(click())
        onView(withId(R.id.text_header)).check(matches(isDisplayed()))
        onView(withId(R.id.button_second)).perform(click())
        onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()))
    }

    @Test
    fun textInputOutput() {
        val result = "This is a test."
        onView(withId(R.id.editText_main)).perform(typeText(result))
        onView(withId(R.id.button_main)).perform(click())
        onView(withId(R.id.text_message)).check(matches(withText(result)));
    }

//    @Test
//    fun textInputOutputFailed() {
//        val result = "This is a test."
//        onView(withId(R.id.editText_main)).perform(typeText(result))
//        onView(withId(R.id.button_main)).perform(click())
//        onView(withId(R.id.text_message)).check(matches(withText("This is a failing test.")))
//    }
}