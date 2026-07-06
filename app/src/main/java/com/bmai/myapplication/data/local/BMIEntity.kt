package com.bmai.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_history")
data class BMIEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Double,
    val category: String,
    val weight: Double,
    val height: Double,
    val age: Int,
    val gender: String,
    val timestamp: Long
)
