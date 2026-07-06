package com.bmai.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class GoalType {
    WEIGHT, BMI, WATER, STEPS, EXERCISE
}

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: GoalType,
    val title: String,
    val targetValue: Double,
    val currentValue: Double,
    val unit: String,
    val deadline: Long? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val reminderEnabled: Boolean = false,
    val reminderTime: String? = null // e.g., "08:00"
)
