package com.example.hometask3

import kotlin.time.Duration

class Habit (
    val name: String,
    val description: String,
    val priority : Priority,
    val type : HabitType,
    val completionAmount : Int,
    val periodicity : Duration,
    val color : Int) : java.io.Serializable
{

}

//val dur : Duration = Duration.days(10)