package com.stas1270.githubapi.ui.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun Activity.hideSoftKeyboard() {
    window.decorView.let {
        it.clearFocus()
        val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethod?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.repeatOnViewLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.() -> Unit
) = viewLifecycleOwner.lifecycleScope.launch {
    repeatOnLifecycle(state, action)
}
