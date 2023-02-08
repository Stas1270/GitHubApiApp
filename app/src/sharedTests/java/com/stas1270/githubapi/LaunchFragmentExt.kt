package com.stas1270.githubapi

import android.content.ComponentName
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

inline fun <reified T : Fragment> launchOurFragment(
    noinline creator: (() -> T)? = null
): ActivityScenario<*> {
    val intent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            EmptyMainActivity::class.java
        )
    )

    return ActivityScenario.launch<EmptyMainActivity>(intent).onActivity {
        val fragment = creator?.invoke() ?: it.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader!!,
            T::class.java.name
        )
        it.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment)
            .commitNow()
    }
}