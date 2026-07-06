package com.bmai.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import com.bmai.myapplication.domain.model.Gender
import com.bmai.myapplication.util.UnitConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(InputUiState())
    val uiState: StateFlow<InputUiState> = _uiState.asStateFlow()

    fun setGender(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun setAge(age: Int) {
        _uiState.update { it.copy(age = age) }
    }

    fun setHeightCm(cm: Float) {
        _uiState.update { it.copy(heightCm = cm.coerceIn(100f, 250f)) }
    }

    fun setWeightKg(kg: Float) {
        _uiState.update { it.copy(weightKg = kg.coerceIn(30f, 250f)) }
    }

    fun toggleHeightUnit() {
        _uiState.update { 
            it.copy(heightUnit = if (it.heightUnit == HeightUnit.CM) HeightUnit.FT else HeightUnit.CM)
        }
    }

    fun toggleWeightUnit() {
        _uiState.update {
            it.copy(weightUnit = if (it.weightUnit == WeightUnit.KG) WeightUnit.LBS else WeightUnit.KG)
        }
    }

    fun setHeightFromFt(feet: Int, inches: Int) {
        val cm = UnitConverter.feetInchesToCm(feet, inches)
        setHeightCm(cm)
    }

    fun setWeightFromLbs(lbs: Float) {
        val kg = UnitConverter.lbsToKg(lbs)
        setWeightKg(kg)
    }

    fun calculateBmi(): Double {
        val state = _uiState.value
        val heightM = state.heightCm / 100.0
        return state.weightKg.toDouble() / (heightM * heightM)
    }
}
