package com.bangkit.alpaca.data.local

import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val keyTextSize = intPreferencesKey("text_size_setting")
    private val keyTextAlignment = intPreferencesKey("text_alignment_setting")
    private val keyTextBackground = intPreferencesKey("text_background_setting")
    private val keyLineHeight = intPreferencesKey("line_height_setting")
    private val keyLineSpacing = intPreferencesKey("line_spacing_setting")

    /**
     * Get customized line spacing from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineSpacing(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyLineSpacing] ?: 1
    }

    /**
     * Save customized line spacing setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineSpacing(value: Int) {
        dataStore.edit { preference ->
            preference[keyLineSpacing] = value
        }
    }

    /**
     * Get customized line height from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineHeight(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyLineHeight] ?: 1
    }

    /**
     * Save customized line height setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineHeight(value: Int) {
        dataStore.edit { preference ->
            preference[keyLineHeight] = value
        }
    }

    /**
     * Get customized text alignment from Datastore.
     * Default value is alignment left.
     *
     * @return Flow
     */
    fun getTextAlignment(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyTextAlignment] ?: View.TEXT_ALIGNMENT_TEXT_START
    }

    /**
     * Save customized text alignment to Datastore
     *
     * @param type Int
     */
    suspend fun saveTextAlignment(type: Int) {
        dataStore.edit { preference ->
            preference[keyTextAlignment] = type
        }
    }

    /**
     * Get customized text size setting from Datastore.
     * Has default value which 18px
     *
     * @return Flow
     */
    fun getTextSize(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyTextSize] ?: 18
    }

    /**
     * Save customized text size setting to DataStore
     *
     * @param textSize Int
     */
    suspend fun saveTextSize(textSize: Int) {
        dataStore.edit { preference ->
            preference[keyTextSize] = textSize
        }
    }

    /**
     * Get customized text background setting from Datastore
     * Has default value which 0
     *
     * @return Flow
     */
    fun getTextBackground(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyTextBackground] ?: 0
    }

    /**
     * Save customized text background setting to DataStore
     *
     * @param type Int
     */
    suspend fun saveTextBackground(type: Int) {
        dataStore.edit { preference ->
            preference[keyTextBackground] = type
        }
    }
}