package com.stas1270.githubapi.ui.utils

import android.content.Context
import android.os.Looper
import android.util.Log
import com.stas1270.githubapi.R

fun checkIsMainThread(text: String) {
    val isMain = Looper.myLooper() == Looper.getMainLooper()
    Log.e("TAG", "$text: isMain: $isMain")
}

fun convertToUiDate(context: Context, string: String) = string.substringBefore(
    "T",
    context.getString(R.string.who_knows)
)