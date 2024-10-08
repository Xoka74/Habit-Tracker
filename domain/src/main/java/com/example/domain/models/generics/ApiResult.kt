package com.example.domain.models.generics

sealed class ApiResult<T> {
    class Success<T>(val data: T) : ApiResult<T>()
    class Error<T>(val code: Int, val message: String?) : ApiResult<T>()
    class Exception<T>(val e: Throwable) : ApiResult<T>()
}
