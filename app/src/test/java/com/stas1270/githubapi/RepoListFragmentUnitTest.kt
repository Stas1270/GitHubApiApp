package com.stas1270.githubapi

import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import com.stas1270.githubapi.ui.repolist.RepoListFragmentDirections
import com.stas1270.githubapi.ui.utils.DEFAULT_REQUEST_ON_LAUNCH
import com.stas1270.githubapi.ui.utils.FAKE_MODEL_COUNT
import com.stas1270.githubapi.utils.BaseRobolectricTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RepoListFragmentUnitTest : BaseRobolectricTest() {

    @get:Rule
    val component = TestMockComponentsRule(ApplicationProvider.getApplicationContext())

    private val response = generateRepoList(DEFAULT_REQUEST_ON_LAUNCH.lowercase(), FAKE_MODEL_COUNT)
    private val navController = mockk<NavController>(relaxed = true)
    private lateinit var scenario: FragmentScenario<RepoListFragment>

    @Before
    fun setUp() {
        coEvery {
            component.mockDataManager.getRepos(DEFAULT_REQUEST_ON_LAUNCH.lowercase())
        } returns flowOf(ResponseData.Success(response))
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_navigate_to_details_screen() = scenario.withFragment {
        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.repo_list)
        val firstItemHolder = recyclerView.requireViewHolderAt(0)
        firstItemHolder.itemView.performClick()

        verify {
            navController
                .navigate(
                    RepoListFragmentDirections
                        .actionRepoListFragmentToRepoDetailsFragment(0)
                )
        }
    }

    @Test
    fun test_launch_repo_list() = scenario.withFragment {
        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.repo_list)
        assertEquals(response.size, recyclerView.adapter!!.itemCount)
        val firstItemHolder = recyclerView.requireViewHolderAt(0)
        val responseData = response[0]

        with(firstItemHolder.itemView) {
            assertEquals(
                responseData.name,
                findViewById<TextView>(R.id.item_repo_name).text
            )
            assertEquals(
                responseData.url,
                findViewById<TextView>(R.id.item_repo_url).text
            )
            assertEquals(
                "Language: ${responseData.language}",
                findViewById<TextView>(R.id.item_repo_language).text
            )
        }
    }

    @Test
    fun test_search_new_data() = scenario.withFragment {
        val searchQuery = "Test Android UI".lowercase()
        val countItems = 3
        val newResponse = generateRepoList(searchQuery, countItems)
        coEvery { component.mockDataManager.getRepos(searchQuery) } returns
                flowOf(ResponseData.Success(newResponse))

        val recyclerView = activity!!.findViewById<RecyclerView>(R.id.repo_list)
        val searchField = activity!!.findViewById<EditText>(R.id.search_repos)
        val searchButton = activity!!.findViewById<ImageButton>(R.id.btn_search)

        searchField.setText(searchQuery)
        searchButton.performClick()
        Robolectric.flushForegroundThreadScheduler()

        assertEquals(countItems, recyclerView.adapter!!.itemCount)

        newResponse.forEachIndexed { index, repoModel ->
            val firstItemHolder = recyclerView.requireViewHolderAt(index)

            with(firstItemHolder.itemView) {
                assertEquals(
                    repoModel.name,
                    findViewById<TextView>(R.id.item_repo_name).text
                )
                assertEquals(
                    repoModel.url,
                    findViewById<TextView>(R.id.item_repo_url).text
                )
                assertEquals(
                    "Language: ${repoModel.language}",
                    findViewById<TextView>(R.id.item_repo_language).text
                )
            }
        }
    }
}