package com.example.hometask3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HabitType : Parcelable {
    Good,
    Bad
}