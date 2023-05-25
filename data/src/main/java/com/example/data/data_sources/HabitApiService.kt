package com.example.data.data_sources

import com.example.domain.models.dto.HabitDto
import com.example.domain.models.requests.HabitDoneBody
import com.example.domain.models.responses.UidBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitApiService {
    @GET("habit")
    suspend fun getHabits(
        @Header("Authorization") token: String
    ): Response<List<HabitDto>>

    @PUT("habit")
    suspend fun addOrUpdate(
        @Header("Authorization") token: String,
        @Body habitDto: HabitDto,
    ): Response<UidBody>

    @DELETE("habit")
    suspend fun deleteHabit(
        @Header("Authorization") token: String,
        @Field("uid") uid: String,
    )

    @POST("habit_done")
    suspend fun habitDone(
        @Header("Authorization") token : String,
        @Body habitDoneBody : HabitDoneBody,
    ) : Response<String>
}