package com.bmai.myapplication.domain.model

import com.bmai.myapplication.ui.theme.*
import androidx.compose.ui.graphics.Color

enum class Gender {
    MALE, FEMALE, CUSTOM
}

enum class UnitSystem {
    METRIC, IMPERIAL
}

enum class BMICategory(val label: String, val color: Color) {
    UNDERWEIGHT("Underweight", UnderweightBlue),
    HEALTHY("Healthy", HealthyGreen),
    OVERWEIGHT("Overweight", OverweightYellow),
    OBESE("Obese", ObeseRed);

    companion object {
        fun fromScore(score: Double): BMICategory {
            return when {
                score < 18.5 -> UNDERWEIGHT
                score < 25.0 -> HEALTHY
                score < 30.0 -> OVERWEIGHT
                else -> OBESE
            }
        }
    }
}

data class BMIResult(
    val score: Double,
    val category: BMICategory,
    val weight: Double,
    val height: Double,
    val age: Int,
    val gender: Gender,
    val timestamp: Long = System.currentTimeMillis()
)
