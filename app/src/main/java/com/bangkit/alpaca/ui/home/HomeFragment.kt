package com.bangkit.alpaca.ui.home

import android.content.Intent
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
import com.bangkit.alpaca.ui.camera.CameraActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
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
        handleViewAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        var isExpanded = false
        binding.apply {
            fabAction.setOnClickListener {
                floatingActionButtonHandler(isExpanded)
                isExpanded = !isExpanded
            }

            fabCamera.setOnClickListener {
                Intent(requireContext(), CameraActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }

            fabGallery.setOnClickListener {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    type = "image/*"
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }.also { intent ->
                    startActivity(intent)
                }
            }

            ivProfileIcon.setOnClickListener {
                Toast.makeText(requireContext(), getString(R.string.clicked), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    /**
     * Manage the FloatingActionButton expand state
     *
     * @param isExpanded Expand state
     * @return Unit
     */
    private fun floatingActionButtonHandler(isExpanded: Boolean) {
        if (!isExpanded) {
            binding.apply {
                fabCamera.show()
                fabGallery.show()
                fabAction.setImageResource(R.drawable.ic_baseline_close_24)
            }
        } else {
            binding.apply {
                fabCamera.hide()
                fabGallery.hide()
                fabAction.setImageResource(R.drawable.ic_baseline_photo_camera_24)
            }
        }
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