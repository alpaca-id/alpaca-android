package com.bangkit.alpaca.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.alpaca.ui.collection.CollectionFragment
import com.bangkit.alpaca.ui.education.EducationFragment

/**
 * SectionPagerAdapter for the TabLayout in the Home Fragment
 */
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