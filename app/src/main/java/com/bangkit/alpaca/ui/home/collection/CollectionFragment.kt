package com.bangkit.alpaca.ui.home.collection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.alpaca.databinding.FragmentCollectionBinding
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.ui.adapter.CollectionListAdapter
import com.bangkit.alpaca.ui.camera.CameraActivity
import com.bangkit.alpaca.utils.animateVisibility
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding

    private val collectionViewModel: CollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewAction()

        collectionViewModel.storiesCollection.observe(viewLifecycleOwner) { stories ->
            if (stories?.isNotEmpty() == true) {
                val recyclerView = binding?.rvCollection
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val adapter = CollectionListAdapter(stories)

                adapter.setOnItemClickCallback(object : CollectionListAdapter.OnItemClickCallback {
                    override fun onItemClicked(story: Story) {
                        // FIXME: Navigate to the ReadingActivity later
                        Toast.makeText(requireContext(), story.title, Toast.LENGTH_SHORT).show()
                    }
                })

                recyclerView?.apply {
                    this.layoutManager = layoutManager
                    this.adapter = adapter
                }
            }

            showEmptyCollectionMessage(stories?.isEmpty() == true)
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.root?.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        binding?.apply {
            btnScanWithCamera.setOnClickListener {
                Intent(requireContext(), CameraActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * Show the empty collection message in the UI
     *
     * @param isVisible State of the message visibility
     */
    private fun showEmptyCollectionMessage(isVisible: Boolean) {

        binding?.apply {
            rvCollection.animateVisibility(!isVisible)
            containerInfoNoCollection.animateVisibility(isVisible)
        }

        if (isVisible) {
            binding?.containerInfoNoCollection?.visibility = View.VISIBLE
            binding?.rvCollection?.visibility = View.GONE
        } else {
            binding?.containerInfoNoCollection?.visibility = View.GONE
            binding?.rvCollection?.visibility = View.VISIBLE
        }
    }
}