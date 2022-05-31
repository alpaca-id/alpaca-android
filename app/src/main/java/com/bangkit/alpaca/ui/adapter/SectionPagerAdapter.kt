package com.bangkit.alpaca.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.alpaca.ui.home.collection.CollectionFragment
import com.bangkit.alpaca.ui.home.education.EducationFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * SectionPagerAdapter for the TabLayout in the Home Fragment
 */
@ExperimentalCoroutinesApi
class SectionPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
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