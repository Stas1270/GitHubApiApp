package com.stas1270.githubapi

import android.app.Activity
import android.os.Looper
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.getFakeRepoModel
import com.stas1270.githubapi.di.TestApplication
import com.stas1270.githubapi.di.mock.TestMockComponentsRule
import com.stas1270.githubapi.ui.MainActivity
import com.stas1270.githubapi.ui.utils.DEFAULT_REQUEST_ON_LAUNCH
import com.stas1270.githubapi.utils.BaseRobolectricTest
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
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

//    @Test
//    fun search_new_data() {
//        val searchQuery = "Test Android UI"
//        val response = generateRepoList(searchQuery, 3)
//        onView(withId(R.id.search_repos))
//            .perform(ViewActions.clearText())
//            .perform(ViewActions.typeText(searchQuery))
//
//        onView(withId(R.id.btn_search))
//            .perform(ViewActions.click())
//        for (i in 0..2) {
//            onView(withId(R.id.repo_list))
//                .perform(scrollToPosition(i))
//                .check(
//                    matches(
//                        atPosition(
//                            i,
//                            Matchers.allOf(
//                                hasDescendant(
//                                    Matchers.allOf(
//                                        withId(R.id.item_repo_name),
//                                        withText(response[i].name)
//                                    )
//                                ),
//                                hasDescendant(
//                                    Matchers.allOf(
//                                        withId(R.id.item_repo_url),
//                                        withText(response[i].url)
//                                    )
//                                )
//                            )
//                        )
//                    )
//                )
//        }
//        Thread.sleep(5000)
//        clickOnItem(2)
//        Thread.sleep(5000)
//        checkDetailsScreen(2)
//    }
//
//    private fun clickOnItem(id: Int) {
//        onView(withId(R.id.repo_list))
//            .perform(actionOnItemAtPosition(id, ViewActions.click()))
//    }
//
//    private fun pressBack() {
//        onView(isRoot()).perform(ViewActions.pressBack())
//    }
//
//    private fun checkDetailsScreen(id: Int) {
//        val response = getFakeRepoDetailedModel(id)
//        onView(withId(R.id.details_repo_name))
//            .check(matches((isDisplayed())))
//            .check(matches(withText(response.name)))
//        onView(withId(R.id.details_repo_description))
//            .check(matches((isDisplayed())))
//            .check(matches(withText("Description: ${response.description}")))
//        onView(withId(R.id.details_repo_url))
//            .check(matches((isDisplayed())))
//            .check(matches(withText("See more at: ${response.htmlUrl}")))
//    }

}

fun <T : Activity> ActivityController<T>.require(): T {
    return get()!!
}

fun <T : Activity> ActivityScenario<T>.with(block: T.() -> Unit) {
    onActivity(block)
}

fun RecyclerView.requireViewHolderAt(position: Int): RecyclerView.ViewHolder {
    scrollToPosition(position)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    return findViewHolderForAdapterPosition(position)!!
}
