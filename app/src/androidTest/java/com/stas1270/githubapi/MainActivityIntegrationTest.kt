package com.stas1270.githubapi

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesViews
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.ui.MainActivity
import com.stas1270.githubapi.utils.actionOnItemAtPosition
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityIntegrationTest {

    init {
        AccessibilityChecks.enable()
            .setRunChecksFromRootView(true)
            .apply {
                setSuppressingResultMatcher(
                    anyOf(
                        matchesViews(withId(R.id.btn_search)),
                        matchesViews(withId(R.id.search_repos))
                    )
                )
            }
    }

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun launchActivity() {
        onView(withId(R.id.repo_list))
            .perform(scrollToPosition(1))
            .check(
                matches(
                    atPosition(
                        1,
                        Matchers.allOf(
                            hasDescendant(
                                Matchers.allOf(withId(R.id.item_repo_name), withText("fake name 1"))
                            ),
                            hasDescendant(
                                Matchers.allOf(
                                    withId(R.id.item_repo_url),
                                    withText("android url.bad 1")
                                )
                            )
                        )
                    )
                )
            )
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_navigate_to_details_screen() {
        val id = 1
        clickOnItem(id)
        checkDetailsScreen(id)
        pressBack()

        val nextId = 3
        clickOnItem(nextId)
        checkDetailsScreen(nextId)
        pressBack()

        onView(withId(R.id.repo_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun search_new_data() {
        val searchQuery = "Test Android UI"
        val response = generateRepoList(searchQuery, 3)
        onView(withId(R.id.search_repos))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(searchQuery))

        onView(withId(R.id.btn_search))
            .perform(ViewActions.click())
        for (i in 0..2) {
            onView(withId(R.id.repo_list))
                .perform(scrollToPosition(i))
                .check(
                    matches(
                        atPosition(
                            i,
                            Matchers.allOf(
                                hasDescendant(
                                    Matchers.allOf(
                                        withId(R.id.item_repo_name),
                                        withText(response[i].name)
                                    )
                                ),
                                hasDescendant(
                                    Matchers.allOf(
                                        withId(R.id.item_repo_url),
                                        withText(response[i].url)
                                    )
                                )
                            )
                        )
                    )
                )
        }
        Thread.sleep(5000)
        clickOnItem(2)
        Thread.sleep(5000)
        checkDetailsScreen(2)
    }

    private fun clickOnItem(id: Int) {
        onView(withId(R.id.repo_list))
            .perform(actionOnItemAtPosition(id, ViewActions.click()))
    }

    private fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    private fun checkDetailsScreen(id: Int) {
        val response = getFakeRepoDetailedModel(id)
        onView(withId(R.id.details_repo_name))
            .check(matches((isDisplayed())))
            .check(matches(withText(response.name)))
        onView(withId(R.id.details_repo_description))
            .check(matches((isDisplayed())))
            .check(matches(withText("Description: ${response.description}")))
        onView(withId(R.id.details_repo_url))
            .check(matches((isDisplayed())))
            .check(matches(withText("See more at: ${response.htmlUrl}")))
    }

}
