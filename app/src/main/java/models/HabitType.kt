package models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HabitType : Parcelable {
    Good,
    Bad
}