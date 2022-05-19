package com.bangkit.alpaca.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.databinding.FragmentLibraryBinding
import com.bangkit.alpaca.ui.adapter.StoriesAdapter
import com.bangkit.alpaca.utils.DataDummy

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding
    private val dummyStories = DataDummy.provideDummyStories()
    private val storiesAdapter: StoriesAdapter by lazy { StoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStories()
    }

    private fun setupStories() {
        storiesAdapter.submitList(dummyStories)
        binding?.rvStoryLibrary?.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}