package com.bangkit.alpaca.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.alpaca.ui.home.collection.CollectionFragment
import com.bangkit.alpaca.ui.home.education.EducationFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * SectionPagerAdapter for the TabLayout in the Home Fragment
 */
@ExperimentalCoroutinesApi
class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CollectionFragment()
            }
            else -> {
                EducationFragment()
            }
        }
    }

}