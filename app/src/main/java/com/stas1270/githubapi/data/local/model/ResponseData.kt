package com.stas1270.githubapi.data.local.model

sealed class ResponseData<out T> {
    data class Success<T>(val data: T) : ResponseData<T>()
    data class Error<T>(val exception: Throwable) : ResponseData<T>()
}

suspend inline fun <T> ResponseData<T>.suspendOnSuccess(
    crossinline onResult: suspend ResponseData.Success<T>.() -> Unit
): ResponseData<T> {
    if (this is ResponseData.Success) {
        onResult(this)
    }
    return this
}

suspend inline fun <T> ResponseData<T>.suspendOnException(
    crossinline onResult: suspend ResponseData<T>.() -> Unit
): ResponseData<T> {
    if (this is ResponseData.Error) {
        onResult(this)
    }
    return this
}
