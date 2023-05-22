package com.example.data.utils

import com.example.domain.models.generics.ApiResult
import retrofit2.HttpException
import retrofit2.Response

object Network {
    suspend fun <T> safeApiCall(execute: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error(code = response.code(), message = response.message())
            }

        } catch (e: HttpException) {
            ApiResult.Error(code = e.code(), message = e.message())
        } catch (e : Throwable){
            ApiResult.Exception(e)
        }
    }
}