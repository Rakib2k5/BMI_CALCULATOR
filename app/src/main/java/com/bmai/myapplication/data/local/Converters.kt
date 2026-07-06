package com.bmai.myapplication.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGoalType(value: GoalType): String {
        return value.name
    }

    @TypeConverter
    fun toGoalType(value: String): GoalType {
        return GoalType.valueOf(value)
    }
}
