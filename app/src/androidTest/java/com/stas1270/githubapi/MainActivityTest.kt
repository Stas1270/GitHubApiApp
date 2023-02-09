package com.stas1270.githubapi

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.data.remote.getFakeRepoModel
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.MainActivity
import com.stas1270.githubapi.ui.utils.DEFAULT_REQUEST_ON_LAUNCH
import com.stas1270.githubapi.utils.actionOnItemAtPosition
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val component = TestMockComponentsRule(ApplicationProvider.getApplicationContext())

    private lateinit var scenario: ActivityScenario<MainActivity>
    private val response = ResponseData.Success(listOf(getFakeRepoModel()))

    @Before
    fun setUp() {
        coEvery { component.mockDataManager.getRepos(DEFAULT_REQUEST_ON_LAUNCH) } returns flowOf(
            ResponseData.Success(generateRepoList(DEFAULT_REQUEST_ON_LAUNCH, 10))
        )
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun launchActivity() {
        onView(withId(R.id.list))
            .perform(scrollToPosition(1))
            .check(
                matches(
                    atPosition(
                        1,
                        Matchers.allOf(
                            hasDescendant(
                                Matchers.allOf(withId(R.id.repo_name), withText("fake name 1"))
                            ),
                            hasDescendant(
                                Matchers.allOf(withId(R.id.repo_url), withText("android url.bad 1"))
                            )
                        )
                    )
                )
            )
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun click_on_item() {
        coEvery { component.mockDataManager.getRepositoryDetails(1) } returns flowOf(
            ResponseData.Success(getFakeRepoDetailedModel(1))
        )
        onView(withId(R.id.list))
            .perform(actionOnItemAtPosition(1, ViewActions.click()))
        //TODO add logic
    }

    @Test
    fun search_new_data() {
        val searchQuery = "Test Android UI"
        coEvery { component.mockDataManager.getRepos(searchQuery.lowercase()) } returns flowOf(
            response
        )

        onView(withId(R.id.search_repos))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(searchQuery))
        onView(withId(R.id.btn_search))
            .perform(ViewActions.click())
        onView(withId(R.id.list))
            .perform(scrollToPosition(0))
            .check(
                matches(
                    atPosition(
                        0,
                        Matchers.allOf(
                            hasDescendant(
                                Matchers.allOf(
                                    withId(R.id.repo_name),
                                    withText(response.data[0].name)
                                )
                            ),
                            hasDescendant(
                                Matchers.allOf(
                                    withId(R.id.repo_url),
                                    withText(response.data[0].url)
                                )
                            )
                        )
                    )
                )
            )
    }
}
