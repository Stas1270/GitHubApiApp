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
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.di.TestAppComponent
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import com.stas1270.githubapi.utils.actionOnItemAtPosition
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var scenario: FragmentScenario<RepoListFragment>
    private val component by lazy {
        val instrument = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val app = instrument.targetContext.applicationContext as TestApplication
        return@lazy app.appComponent as TestAppComponent
    }

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
//        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun launchList() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .perform(scrollToPosition(1))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        1,
                        Matchers.allOf(
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.repo_name),
                                    ViewMatchers.withText("fake name 1")
                                )
                            ),
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.repo_url),
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
    fun click_on_item() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .perform(actionOnItemAtPosition(1, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.repo_name))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.avatar))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
    }

    @Test
    fun search_new_data() {
        val searchQuery = "Test Android UI"
        val fakeRepoModel = generateRepoList(searchQuery, 10)[0]
        Espresso.onView(ViewMatchers.withId(R.id.search_repos))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(searchQuery))
        Espresso.onView(ViewMatchers.withId(R.id.btn_search))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .perform(scrollToPosition(0))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        0,
                        Matchers.allOf(
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.repo_name),
                                    ViewMatchers.withText(fakeRepoModel.name)
                                )
                            ),
                            ViewMatchers.hasDescendant(
                                Matchers.allOf(
                                    ViewMatchers.withId(R.id.repo_url),
                                    ViewMatchers.withText(fakeRepoModel.url)
                                )
                            )
                        )
                    )
                )
            )
    }
}