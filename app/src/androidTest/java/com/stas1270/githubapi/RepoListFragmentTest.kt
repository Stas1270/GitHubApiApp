package com.stas1270.githubapi

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.data.remote.getFakeRepoModel
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import com.stas1270.githubapi.ui.utils.DEFAULT_REQUEST_ON_LAUNCH
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var scenario: FragmentScenario<RepoListFragment>

    @get:Rule
    val component = TestMockComponentsRule(ApplicationProvider.getApplicationContext())

    private val response = ResponseData.Success(listOf(getFakeRepoModel()))

    @Before
    fun setUp() {
        coEvery {
            component.mockDataManager.getRepos(DEFAULT_REQUEST_ON_LAUNCH.lowercase())
        } returns
                flowOf(
                    ResponseData.Success(
                        generateRepoList(DEFAULT_REQUEST_ON_LAUNCH.lowercase(), 10)
                    )
                )
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun launchList() {
        Espresso.onView(ViewMatchers.withId(R.id.repo_list))
            .perform(scrollToPosition(1))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        1,
                        Matchers.allOf(
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.item_repo_name),
                                    ViewMatchers.withText("fake name 1")
                                )
                            ),
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.item_repo_url),
                                    ViewMatchers.withText("android url.bad 1")
                                )
                            )
                        )
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.progress_bar))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }


    @Test
    fun search_new_data() {
        val searchQuery = "Test Android UI"
        coEvery { component.mockDataManager.getRepos(searchQuery.lowercase()) } returns flowOf(
            response
        )
        Espresso.onView(ViewMatchers.withId(R.id.search_repos))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(searchQuery))
        Espresso.onView(ViewMatchers.withId(R.id.btn_search))
            .perform(ViewActions.click())


        Espresso.onView(ViewMatchers.withId(R.id.repo_list))
            .perform(scrollToPosition(0))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        0,
                        Matchers.allOf(
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.item_repo_name),
                                    ViewMatchers.withText(response.data[0].name)
                                )
                            ),
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.item_repo_url),
                                    ViewMatchers.withText(response.data[0].url)
                                )
                            )
                        )
                    )
                )
            )
    }
}