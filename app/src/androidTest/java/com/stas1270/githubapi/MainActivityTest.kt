package com.stas1270.githubapi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.stas1270.githubapi.di.TestAppComponent
import com.stas1270.githubapi.ui.MainActivity
import com.stas1270.githubapi.utils.atPosition
import com.stas1270.githubapi.utils.scrollToPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

//    private val server by lazy { MockWebServer() }

    @get:Rule val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
//        server.start(4000)
//        URL.BASE_URL = server.url("/").toString()

        val instrument = InstrumentationRegistry.getInstrumentation()
        val app = instrument.targetContext.applicationContext as GitHubApp
        val component = app.appComponent as TestAppComponent
        component.inject(this)
    }

    @Test
    fun launchActivity() {
        activityRule.launchActivity(null)

        onView(withId(R.id.list))
            .perform(scrollToPosition(0))
            .check(matches(atPosition(0, withText("Cats: 1 … 2"))))

//        onView(withId(R.id.btnLoad))
//                .check(matches(withText("Load")))
//                .check(matches(isClickable()))
//
//        onView(withId(R.id.progressBar))
//                .check(matches(not(isDisplayed())))
    }
//
//    @Test
//    fun type_user_we_rock_star_should_see_WeRockStar_and_repo_url() {
//        activityRule.launchActivity(null)
//
//        typeUserWeRockStar()
//    }
//
//    @Test
//    fun type_user_we_rock_star_and_click_on_repo_url_should_see_list_of_repo() {
//        activityRule.launchActivity(null)
//
//        typeUserWeRockStar()
//
//        onView(withId(R.id.tvRepo)).perform(click())
//        onView(withId(R.id.smootProgressBar)).check(matches(isDisplayed()))
//        onView(withId(R.id.rvList)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun user_not_found_should_no_see_username() {
//        MockResponse().setResponseCode(404)
//
//        activityRule.launchActivity(null)
//
//        onView(withId(R.id.edtUsername))
//                .perform(typeText(""))
//        onView(withId(R.id.btnLoad))
//                .perform(click())
//
//        onView(withId(R.id.tvUsername))
//                .check(matches(withText("")))
//        onView(withId(R.id.tvRepo))
//                .check(matches(withText("")))
//    }
//
//    private fun typeUserWeRockStar() {
//        val username = "WeRockStar"
//        val repoUrl = "https://api.github.com/users/WeRockStar/repos"
//
//        setUpMockResponse()
//
//        onView(withId(R.id.edtUsername))
//                .perform(typeText(username))
//        onView(withId(R.id.btnLoad))
//                .perform(click())
//        onView(withId(R.id.tvUsername))
//                .check(matches(withText(username)))
//        onView(withId(R.id.tvRepo))
//                .check(matches(withText(repoUrl)))
//    }
//
//    private fun setUpMockResponse() {
//        server.setDispatcher(object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest?): MockResponse {
//                val path = request?.path?.split("/")
//                val endpoint = path?.last()
//                return when (endpoint) {
//                    "repos" -> MockResponse().setBody("repo.json".toJsonString())
//                    else -> MockResponse()
//                            .setResponseCode(200)
//                            .setBody("github_profile.json".toJsonString())
//                }
//            }
//        })
//    }
//
//    @After
//    fun tearDown() {
//        server.shutdown()
//    }
}
