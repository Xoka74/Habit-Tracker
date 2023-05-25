package com.example.domain.models.converters

import androidx.room.TypeConverter

class DoneDatesConverter {

    @TypeConverter
    fun fromDoneDates(values : List<Long>) : String{
        return values.joinToString()
    }

    @TypeConverter
    fun toDoneDates(src : String) : List<Long> {
        if (src == "") return listOf()
        return src.split(", ").map { it.toLong() }
    }
}