package com.example.hometask3.data.services

import com.example.hometask3.data.models.dto.HabitDto
import com.example.hometask3.data.models.responses.UidBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface HabitApiService {
    @GET("habit")
    suspend fun getHabits(
        @Header("Authorization") auth: String
    ): Response<List<HabitDto>>

    @PUT("habit")
    suspend fun addOrUpdate(
        @Header("Authorization") auth: String,
        @Body habitDto: HabitDto,
    ) : Response<UidBody>

    @DELETE("habit")
    suspend fun deleteHabit(
        @Header("Authorization") auth: String,
        @Field("uid") uid: String,
    )
}