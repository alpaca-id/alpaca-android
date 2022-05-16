package com.bangkit.alpaca.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentHomeBinding
import com.bangkit.alpaca.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()

        binding.ivProfileIcon.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.clicked), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Initialize a TabLayout with ViewPager2
     *
     * @return Unit
     */
    private fun setViewPager() {
        val viewPager = binding.viewPager
        val tabs = binding.tabLayout
        val tabTitles = intArrayOf(
            R.string.title_koleksi_bacaan,
            R.string.title_edukasi
        )

        viewPager.adapter = SectionPagerAdapter(activity as AppCompatActivity)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(tabTitles[position])
        }.attach()
    }
}