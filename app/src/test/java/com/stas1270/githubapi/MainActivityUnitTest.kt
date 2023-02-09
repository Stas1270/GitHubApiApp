package com.stas1270.githubapi

import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.data.remote.getFakeRepoModel
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.MainActivity
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.utils.DEFAULT_REQUEST_ON_LAUNCH
import com.stas1270.githubapi.utils.BaseRobolectricTest
import com.stas1270.githubapi.utils.require
import com.stas1270.githubapi.utils.requireViewHolderAt
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainActivityTest : BaseRobolectricTest() {

    @get:Rule
    val component = TestMockComponentsRule(ApplicationProvider.getApplicationContext())

    private val responseData = getFakeRepoModel()
    private val response = ResponseData.Success(listOf(responseData))

    private lateinit var controller: ActivityController<MainActivity>

    @Before
    fun setUp() {
        coEvery {
            component.mockDataManager.getRepos(DEFAULT_REQUEST_ON_LAUNCH.lowercase())
        } returns flowOf(response)
        controller = Robolectric.buildActivity(MainActivity::class.java)
        controller.setup()
    }

    @After
    fun tearDown() {
        controller.close()
    }

    @Test
    fun test_launch_activity() {
        val activity = controller.require()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.repo_list)
        val firstItemHolder = recyclerView.requireViewHolderAt(0)

        assertEquals(1, recyclerView.adapter!!.itemCount)

        with(firstItemHolder.itemView) {
            assertEquals(responseData.name, findViewById<TextView>(R.id.item_repo_name).text)
            assertEquals(responseData.url, findViewById<TextView>(R.id.item_repo_url).text)
            assertEquals(
                "Language: ${responseData.language}",
                findViewById<TextView>(R.id.item_repo_language).text
            )
        }
    }

    @Test
    fun test_search_new_data() {
        val searchQuery = "Test Android UI".lowercase()
        val countItems = 3
        val newResponse = generateRepoList(searchQuery, countItems)
        coEvery { component.mockDataManager.getRepos(searchQuery) } returns
                flowOf(ResponseData.Success(newResponse))
        val activity = controller.require()

        val recyclerView = activity.findViewById<RecyclerView>(R.id.repo_list)
        val searchField = activity.findViewById<EditText>(R.id.search_repos)
        val searchButton = activity.findViewById<ImageButton>(R.id.btn_search)

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

    @Test
    fun test_navigate_to_details_screen_and_back() {
        val id = 123
        val responseDetailedData = getFakeRepoDetailedModel(id)
        coEvery {
            component.mockDataManager.getRepositoryDetails(responseData.id)
        } returns flowOf(ResponseData.Success(responseDetailedData))

        val activity = controller.require()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.repo_list)
        recyclerView.requireViewHolderAt(0).itemView.performClick()
        Robolectric.flushForegroundThreadScheduler()
        checkDetailsScreen(responseDetailedData)
    }

    private fun checkDetailsScreen(responseData: RepoDetailedModel) {
        val activity = controller.require()
        with(activity) {
            assertEquals(
                responseData.name,
                findViewById<TextView>(R.id.details_repo_name).text
            )
            assertEquals(
                "Description: ${responseData.description}",
                findViewById<TextView>(R.id.details_repo_description).text
            )
            assertEquals(
                "See more at: ${responseData.htmlUrl}",
                findViewById<TextView>(R.id.details_repo_url).text
            )
            MatcherAssert.assertThat(
                findViewById<TextView>(R.id.repo_created_at).text.toString(),
                CoreMatchers.startsWith("Created at: ")
            )
            MatcherAssert.assertThat(
                findViewById<TextView>(R.id.repo_updated_at).text.toString(),
                CoreMatchers.startsWith("Updated at: ")
            )
        }
    }
}
