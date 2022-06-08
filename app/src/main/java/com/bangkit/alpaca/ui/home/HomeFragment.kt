package com.bangkit.alpaca.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentHomeBinding
import com.bangkit.alpaca.ui.adapter.SectionPagerAdapter
import com.bangkit.alpaca.ui.camera.CameraActivity
import com.bangkit.alpaca.ui.processing.ProcessingActivity
import com.bangkit.alpaca.ui.processing.ProcessingActivity.Companion.EXTRA_IMAGE
import com.bangkit.alpaca.ui.profile.ProfileActivity
import com.bangkit.alpaca.utils.MediaUtility.uriToFile
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Get the result from the gallery and convert the URI to File
            val selectedImg: Uri = result.data?.data as Uri
            val picture = uriToFile(selectedImg, requireContext())

            // Send File to the ProcessingActivity to start upload process
            Intent(requireActivity(), ProcessingActivity::class.java).also { intent ->
                intent.putExtra(EXTRA_IMAGE, picture)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
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
     * Start an intent to select a picture from the gallery
     */
    private fun startIntentGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        var isExpanded = false
        binding?.apply {
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
                startIntentGallery()
            }

            ivProfileIcon.setOnClickListener {
                Intent(requireContext(), ProfileActivity::class.java).also { intent ->
                    startActivity(intent)
                }
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
            binding?.apply {
                fabCamera.show()
                fabGallery.show()
                fabAction.setImageResource(R.drawable.ic_baseline_close_24)
            }
        } else {
            binding?.apply {
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
        val viewPager = binding?.viewPager
        val tabs = binding?.tabLayout
        val tabTitles = intArrayOf(
            R.string.title_koleksi_bacaan,
            R.string.title_edukasi
        )

        viewPager?.adapter = SectionPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }
}