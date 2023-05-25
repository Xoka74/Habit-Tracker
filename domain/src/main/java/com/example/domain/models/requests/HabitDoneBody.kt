package com.example.domain.models.requests

import com.google.gson.annotations.SerializedName

data class HabitDoneBody(
    @SerializedName("habit_uid") val uid: String,
    val date: Long,
)