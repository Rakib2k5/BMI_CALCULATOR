package com.bmai.myapplication.ui.screens

import com.bmai.myapplication.domain.model.Gender

data class InputUiState(
    val gender: Gender = Gender.MALE,
    val age: Int = 28,
    val heightCm: Float = 178f,
    val weightKg: Float = 74.5f,
    val heightUnit: HeightUnit = HeightUnit.CM,
    val weightUnit: WeightUnit = WeightUnit.KG
)

enum class HeightUnit { CM, FT }
enum class WeightUnit { KG, LBS }
