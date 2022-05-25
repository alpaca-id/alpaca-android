package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.local.SettingPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TextStyleRepository @Inject constructor(private val settingPreferences: SettingPreferences) {

    /**
     * Get customized line spacing from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineSpacingPreference(): Flow<Int> = settingPreferences.getLineSpacing()

    /**
     * Save customized line spacing setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineSpacingPreference(value: Int) {
        settingPreferences.saveLineSpacing(value)
    }

    /**
     * Get customized line height from Datastore
     * Default value is 1
     *
     * @return Flow
     */
    fun getLineHeightPreference(): Flow<Int> = settingPreferences.getLineHeight()

    /**
     * Save customized line height setting to the Datastore
     *
     * @param value Int
     */
    suspend fun saveLineHeightPreference(value: Int) {
        settingPreferences.saveLineHeight(value)
    }

    /**
     * Get customized text alignment from Datastore.
     * Default value is alignment left.
     *
     * @return Flow
     */
    fun getTextAlignmentPreference(): Flow<Int> = settingPreferences.getTextAlignment()

    /**
     * Save customized text alignment to Datastore
     *
     * @param type Int
     */
    suspend fun saveTextAlignmentPreference(type: Int) {
        settingPreferences.saveTextAlignment(type)
    }

    /**
     * Get customized text size setting from Datastore.
     * Has default value which 18px
     *
     * @return Flow
     */
    fun getTextSizePreference(): Flow<Int> = settingPreferences.getTextSize()

    /**
     * Save customized text size setting to DataStore
     *
     * @param size Int
     */
    suspend fun saveTextSizePreference(size: Int) {
        settingPreferences.saveTextSize(size)
    }
}