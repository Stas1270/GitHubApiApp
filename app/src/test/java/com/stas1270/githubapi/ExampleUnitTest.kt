package com.stas1270.githubapi

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Before
    fun setup() {
//        val component: TestAppComponent = DaggerTestAppComponent.builder()
//            .appModule(AppModule(this))
//            .build()
//        component.inject(this)
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}