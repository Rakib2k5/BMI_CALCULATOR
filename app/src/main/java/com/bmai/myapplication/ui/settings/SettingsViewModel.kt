package com.bmai.myapplication.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmai.myapplication.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = combine(
        repository.themeMode,
        repository.heightUnit,
        repository.weightUnit,
        repository.reminderDaily
    ) { theme, height, weight, reminder ->
        SettingsUiState(
            themeMode = theme,
            heightUnit = height,
            weightUnit = weight,
            reminderDaily = reminder
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setThemeMode(mode: String) {
        viewModelScope.launch { repository.setThemeMode(mode) }
    }

    fun setHeightUnit(unit: String) {
        viewModelScope.launch { repository.setHeightUnit(unit) }
    }

    fun setWeightUnit(unit: String) {
        viewModelScope.launch { repository.setWeightUnit(unit) }
    }

    fun setReminderDaily(enabled: Boolean) {
        viewModelScope.launch { repository.setReminderDaily(enabled) }
    }
}
