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


    /**
     * Get customized line spacing from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineSpacing(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[LINE_SPACING_KEY] ?: 1
    }

    /**
     * Save customized line spacing setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineSpacing(value: Int) {
        dataStore.edit { preference ->
            preference[LINE_SPACING_KEY] = value
        }
    }

    /**
     * Get customized line height from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineHeight(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[LINE_HEIGHT_KEY] ?: 1
    }

    /**
     * Save customized line height setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineHeight(value: Int) {
        dataStore.edit { preference ->
            preference[LINE_HEIGHT_KEY] = value
        }
    }

    /**
     * Get customized text alignment from Datastore.
     * Default value is alignment left.
     *
     * @return Flow
     */
    fun getTextAlignment(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[TEXT_ALIGNMENT_KEY] ?: View.TEXT_ALIGNMENT_TEXT_START
    }

    /**
     * Save customized text alignment to Datastore
     *
     * @param type Int
     */
    suspend fun saveTextAlignment(type: Int) {
        dataStore.edit { preference ->
            preference[TEXT_ALIGNMENT_KEY] = type
        }
    }

    /**
     * Get customized text size setting from Datastore.
     * Has default value which 18px
     *
     * @return Flow
     */
    fun getTextSize(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[TEXT_SIZE_KEY] ?: 18
    }

    /**
     * Save customized text size setting to DataStore
     *
     * @param textSize Int
     */
    suspend fun saveTextSize(textSize: Int) {
        dataStore.edit { preference ->
            preference[TEXT_SIZE_KEY] = textSize
        }
    }

    companion object {
        private val TEXT_SIZE_KEY = intPreferencesKey("text_size_setting")
        private val TEXT_ALIGNMENT_KEY = intPreferencesKey("text_alignment_setting")
        private val LINE_HEIGHT_KEY = intPreferencesKey("line_height_setting")
        private val LINE_SPACING_KEY = intPreferencesKey("line_spacing_setting")
    }
}