package com.example.wage_manager.data

import androidx.room.TypeConverter
import java.util.*

class Convertors {
    @TypeConverter
    fun fromDateList(value: List<Date>): String {
        return value.joinToString(",") { it.time.toString() }
    }

    @TypeConverter
    fun toDateList(value: String): List<Date> {
        return value.split(",").mapNotNull {
            try {
                Date(it.toLong())
            } catch (e: NumberFormatException) {
                null
            }
        }
    }
}