package com.stas1270.githubapi.utils

import android.app.Activity
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController


fun <T : Activity> ActivityController<T>.require(): T {
    return get()!!
}

fun RecyclerView.requireViewHolderAt(position: Int): RecyclerView.ViewHolder {
    scrollToPosition(position)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    return findViewHolderForAdapterPosition(position)!!
}
