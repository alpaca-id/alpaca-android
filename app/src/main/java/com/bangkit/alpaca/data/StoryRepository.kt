package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.model.Story
import com.bangkit.alpaca.utils.DataDummy

class StoryRepository {

    fun getSavedStories(): List<Story> {
        return DataDummy.provideDummyStories()
    }
}