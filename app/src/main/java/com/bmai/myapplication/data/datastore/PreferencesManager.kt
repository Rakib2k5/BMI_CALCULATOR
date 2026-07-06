package com.bmai.myapplication.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val THEME_MODE = stringPreferencesKey("theme_mode") // LIGHT, DARK, SYSTEM
        val HEIGHT_UNIT = stringPreferencesKey("height_unit") // CM, FT
        val WEIGHT_UNIT = stringPreferencesKey("weight_unit") // KG, LBS
        val REMINDER_DAILY = booleanPreferencesKey("reminder_daily")
        val BIOLOGICAL_SEX = stringPreferencesKey("biological_sex")
        val ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
    }

    val themeMode: Flow<String> = context.dataStore.data.map { it[PreferencesKeys.THEME_MODE] ?: "SYSTEM" }
    val heightUnit: Flow<String> = context.dataStore.data.map { it[PreferencesKeys.HEIGHT_UNIT] ?: "CM" }
    val weightUnit: Flow<String> = context.dataStore.data.map { it[PreferencesKeys.WEIGHT_UNIT] ?: "KG" }
    val reminderDaily: Flow<Boolean> = context.dataStore.data.map { it[PreferencesKeys.REMINDER_DAILY] ?: true }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { it[PreferencesKeys.THEME_MODE] = mode }
    }

    suspend fun setHeightUnit(unit: String) {
        context.dataStore.edit { it[PreferencesKeys.HEIGHT_UNIT] = unit }
    }

    suspend fun setWeightUnit(unit: String) {
        context.dataStore.edit { it[PreferencesKeys.WEIGHT_UNIT] = unit }
    }

    suspend fun setReminderDaily(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.REMINDER_DAILY] = enabled }
    }
}
