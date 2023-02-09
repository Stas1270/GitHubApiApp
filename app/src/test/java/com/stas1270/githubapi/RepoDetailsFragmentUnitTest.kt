package com.stas1270.githubapi

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import com.stas1270.githubapi.utils.BaseRobolectricTest
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RepoDetailsFragmentUnitTest : BaseRobolectricTest() {

    private val navController = mockk<NavController>(relaxed = true)
    private lateinit var scenario: FragmentScenario<RepoDetailsFragment>

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
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_GithubApiApp,
            fragmentArgs = args
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_launch_details_screen() = scenario.withFragment {
        with(activity!!) {
            TestCase.assertEquals(
                responseData.name,
                findViewById<TextView>(R.id.details_repo_name).text
            )
            TestCase.assertEquals(
                "Description: ${responseData.description}",
                findViewById<TextView>(R.id.details_repo_description).text
            )
            TestCase.assertEquals(
                "See more at: ${responseData.htmlUrl}",
                findViewById<TextView>(R.id.details_repo_url).text
            )
            assertThat(
                findViewById<TextView>(R.id.repo_created_at).text.toString(),
                startsWith("Created at: ")
            )
            assertThat(
                findViewById<TextView>(R.id.repo_updated_at).text.toString(),
                startsWith("Updated at: ")
            )
        }
    }
}