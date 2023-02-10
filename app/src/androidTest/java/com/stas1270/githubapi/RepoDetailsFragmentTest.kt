package com.stas1270.githubapi

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.startsWith
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RepoDetailsFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var scenario: FragmentScenario<RepoDetailsFragment>

    //studio issue https://issuetracker.google.com/issues/232007221
    @get:Rule
    val component = TestMockComponentsRule(ApplicationProvider.getApplicationContext())

    private val id = 123
    private val responseData = getFakeRepoDetailedModel(id)
    private val response = ResponseData.Success(responseData)

    @Before
    fun setUp() {
        coEvery {
            component.mockDataManager.getRepositoryDetails(id)
        } returns flowOf(response)

        val args = bundleOf("repo_id" to id)
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_GithubApiApp,
            fragmentArgs = args
        )
        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.repoDetailsFragment, args)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_launch_details_screen() {
        Espresso.onView(ViewMatchers.withId(R.id.details_repo_name))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
            .check(ViewAssertions.matches(ViewMatchers.withText(responseData.name)))

        Espresso.onView(ViewMatchers.withId(R.id.details_repo_description))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
            .check(ViewAssertions.matches(ViewMatchers.withText("Description: ${responseData.description}")))

        Espresso.onView(ViewMatchers.withId(R.id.details_repo_url))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
            .check(ViewAssertions.matches(ViewMatchers.withText("See more at: ${responseData.htmlUrl}")))

        Espresso.onView(ViewMatchers.withId(R.id.repo_created_at))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
            .check(ViewAssertions.matches(ViewMatchers.withText(startsWith("Created at: "))))

        Espresso.onView(ViewMatchers.withId(R.id.repo_updated_at))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
            .check(ViewAssertions.matches(ViewMatchers.withText(startsWith("Updated at: "))))
    }
}