package com.bangkit.alpaca.ui.library

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.R
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
        setupSearchBar()
    }

    private fun setupStories() {
        storiesAdapter.submitList(dummyStories)
        binding?.rvStoryLibrary?.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearchBar() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            binding?.toolbarProfile?.menu?.findItem(R.id.library_search)?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryHint = "Cari cerita"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchNotFound(query)
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun searchNotFound(query: String) {
        binding?.apply {
            tvSearchResult.visibility = View.VISIBLE
            containerSearchNotFound.visibility = View.VISIBLE
            tvNewCollection.visibility = View.GONE
            rvStoryLibrary.visibility = View.GONE

            tvSearchResult.text = getString(R.string.search_result, 0)
            tvSearchNotFound.text = getString(R.string.result_not_found2, query)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}