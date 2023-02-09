package com.stas1270.githubapi

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavCompTest {

    private lateinit var navController: TestNavHostController
    private lateinit var scenario: FragmentScenario<RepoListFragment>

    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubApiApp)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun navigate_to_details_screen() {
        val position = 0
        Thread.sleep(5000)

        Espresso.onView(withId(R.id.repo_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        position,
                        ViewActions.click()
                    )
            )

        Assert.assertEquals(navController.currentDestination?.id, R.id.repoDetailsFragment)
    }
}