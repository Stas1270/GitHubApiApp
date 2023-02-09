package com.stas1270.githubapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stas1270.githubapi.utils.TestDispatcherRule
import io.mockk.junit4.MockKRule
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mockkRule = MockKRule(this)
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

}