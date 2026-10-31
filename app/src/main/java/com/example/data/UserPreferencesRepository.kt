package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "world_time_lock_prefs")

data class UserTimePreferences(
    val pinnedCountryIds: Set<String> = DEFAULT_PINNED_IDS,
    val anchorCountryId: String = DEFAULT_ANCHOR_ID,
    val is24HourFormat: Boolean = true,
    val isTimeLocked: Boolean = false,
    val lockedOffsetMinutes: Int = 0
) {
    companion object {
        val DEFAULT_PINNED_IDS = setOf(
            "US", "GB", "IN", "JP", "AU", "AE", "DE", "SG", "BR", "ZA"
        )
        const val DEFAULT_ANCHOR_ID = "GB"
    }
}

class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val PINNED_IDS = stringSetPreferencesKey("pinned_country_ids")
        val ANCHOR_ID = stringPreferencesKey("anchor_country_id")
        val IS_24_HOUR = booleanPreferencesKey("is_24_hour_format")
        val IS_TIME_LOCKED = booleanPreferencesKey("is_time_locked")
        val LOCKED_OFFSET_MINUTES = intPreferencesKey("locked_offset_minutes")
    }

    val preferencesFlow: Flow<UserTimePreferences> = context.dataStore.data.map { prefs ->
        UserTimePreferences(
            pinnedCountryIds = prefs[Keys.PINNED_IDS] ?: UserTimePreferences.DEFAULT_PINNED_IDS,
            anchorCountryId = prefs[Keys.ANCHOR_ID] ?: UserTimePreferences.DEFAULT_ANCHOR_ID,
            is24HourFormat = prefs[Keys.IS_24_HOUR] ?: true,
            isTimeLocked = prefs[Keys.IS_TIME_LOCKED] ?: false,
            lockedOffsetMinutes = prefs[Keys.LOCKED_OFFSET_MINUTES] ?: 0
        )
    }

    suspend fun togglePinCountry(countryId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.PINNED_IDS] ?: UserTimePreferences.DEFAULT_PINNED_IDS
            prefs[Keys.PINNED_IDS] = if (current.contains(countryId)) {
                current - countryId
            } else {
                current + countryId
            }
        }
    }

    suspend fun setAnchorCountry(countryId: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ANCHOR_ID] = countryId
        }
    }

    suspend fun toggle24HourFormat() {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.IS_24_HOUR] ?: true
            prefs[Keys.IS_24_HOUR] = !current
        }
    }

    suspend fun setTimeLockState(isLocked: Boolean, offsetMinutes: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_TIME_LOCKED] = isLocked
            prefs[Keys.LOCKED_OFFSET_MINUTES] = offsetMinutes.coerceIn(-1440, 1440)
        }
    }
}
