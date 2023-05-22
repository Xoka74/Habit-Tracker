package com.example.domain.models.dto

import com.google.gson.annotations.SerializedName

data class HabitDto(
    @SerializedName("done_dates") val doneDates: List<Int>,
    val color: Int,
    val count: Int,
    val date: Long,
    val description: String,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String,
)