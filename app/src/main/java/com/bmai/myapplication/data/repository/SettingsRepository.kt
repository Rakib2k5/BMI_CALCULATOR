package com.bmai.myapplication.data.repository

import com.bmai.myapplication.data.datastore.PreferencesManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface SettingsRepository {
    val themeMode: Flow<String>
    val heightUnit: Flow<String>
    val weightUnit: Flow<String>
    val reminderDaily: Flow<Boolean>

    suspend fun setThemeMode(mode: String)
    suspend fun setHeightUnit(unit: String)
    suspend fun setWeightUnit(unit: String)
    suspend fun setReminderDaily(enabled: Boolean)
}

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : SettingsRepository {
    override val themeMode: Flow<String> = preferencesManager.themeMode
    override val heightUnit: Flow<String> = preferencesManager.heightUnit
    override val weightUnit: Flow<String> = preferencesManager.weightUnit
    override val reminderDaily: Flow<Boolean> = preferencesManager.reminderDaily

    override suspend fun setThemeMode(mode: String) = preferencesManager.setThemeMode(mode)
    override suspend fun setHeightUnit(unit: String) = preferencesManager.setHeightUnit(unit)
    override suspend fun setWeightUnit(unit: String) = preferencesManager.setWeightUnit(unit)
    override suspend fun setReminderDaily(enabled: Boolean) = preferencesManager.setReminderDaily(enabled)
}
