package com.stas1270.githubapi

import android.content.Context
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpinnerSelectionTest {

    @get:Rule
    val activity = ActivityScenarioRule(PhoneNumberActivity::class.java)

    @Test
    open fun iterateSpinnerItems() {
        val myArray = getResourceArray(R.array.labels_array)
        // Iterate through the spinner array of items.
        val size = myArray.size
        for (i in 0 until size) {
            // Find the spinner and click on it.
            onView(withId(R.id.label_spinner)).perform(click())
            // Find the spinner item and click on it.
            onData(Matchers.`is`(myArray[i])).perform(click())
            // Find the text view and check that the spinner item
            // is part of the string.
            onView(withId(R.id.text_phonelabel))
                .check(matches(withText(Matchers.containsString(myArray[i]))))
        }
    }

    private fun getResourceArray(id: Int): Array<String?> {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        return targetContext.getResources().getStringArray(id)
    }
}