package com.bmai.myapplication.ui.settings

data class SettingsUiState(
    val themeMode: String = "SYSTEM",
    val heightUnit: String = "CM",
    val weightUnit: String = "KG",
    val reminderDaily: Boolean = true,
    val username: String = "Guest User",
    val email: String = "guest@vitaltrack.com",
    val appVersion: String = "1.0.0"
)
