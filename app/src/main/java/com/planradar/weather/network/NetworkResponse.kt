package com.planradar.weather.network

import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()
    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, Loading progress bar
     */
    object Loading: NetworkResponse<Nothing, Nothing>()
}
