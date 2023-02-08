package com.stas1270.githubapi

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.di.TestAppComponent
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    private lateinit var scenario: FragmentScenario<RepoListFragment>
    private val component by lazy {
        val instrument = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val app = instrument.targetContext.applicationContext as TestApplication
        return@lazy app.appComponent as TestAppComponent
    }

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun sdfsd() {
        coEvery { component.repoRepository().getRepos("") } returns
                flowOf(ResponseData.Success(generateRepoList(1)))

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
                                    ViewMatchers.withText("url.bad 1")
                                )
                            )
                        )
                    )
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.progress_bar))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }
}