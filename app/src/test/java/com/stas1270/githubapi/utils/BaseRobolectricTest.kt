package com.stas1270.githubapi.utils

import com.stas1270.githubapi.BaseTest
import org.junit.Rule

open class BaseRobolectricTest : BaseTest() {
    @get:Rule
    val fakeImageLoaderRule = FakeImageLoaderRule()

}