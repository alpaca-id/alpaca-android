package com.bangkit.alpaca.ui.library

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.FragmentLibraryBinding
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.ui.adapter.LibraryListAdapter
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding
    private val libraryListAdapter: LibraryListAdapter by lazy { LibraryListAdapter() }
    private val libraryViewModel: LibraryViewModel by viewModels()

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
        setupAction()
    }

    private fun setupAction() {
        libraryListAdapter.setOnItemClickCallback(object : LibraryListAdapter.OnItemClickCallback {
            override fun onItemClicked(story: Story) {
                val toReadingActivity =
                    LibraryFragmentDirections.actionNavigationLibraryToReadingActivity(story)

                binding?.root?.findNavController()
                    ?.navigate(toReadingActivity)
            }
        })
    }

    private fun setupStories() {
        libraryViewModel.storiesLibrary.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(requireContext(), false)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    val stories = result.data
                    libraryListAdapter.submitList(stories)
                    binding?.rvStoryLibrary?.apply {
                        adapter = libraryListAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(requireContext())
                }
            }
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
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        libraryViewModel.findBook(newText).observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> LoadingDialog.displayLoading(
                                    requireContext(),
                                    false
                                )
                                is Result.Success -> {
                                    LoadingDialog.hideLoading()
                                    val stories = result.data
                                    if (stories.isNotEmpty()) {
                                        searchResult(isNotFound = false)
                                        libraryListAdapter.submitList(stories)
                                        binding?.rvStoryLibrary?.apply {
                                            adapter = libraryListAdapter
                                            layoutManager = LinearLayoutManager(requireContext())
                                        }
                                    } else {
                                        searchResult(newText, isNotFound = true)
                                    }
                                }
                                is Result.Error -> {
                                    LoadingDialog.hideLoading()
                                    result.error.showToastMessage(requireContext())
                                }
                            }
                        }
                    }
                    return false
                }

            })
        }
    }

    private fun searchResult(query: String = "", isNotFound: Boolean) {
        if (isNotFound) {
            binding?.apply {
                containerSearchNotFound.visibility = View.VISIBLE
                rvStoryLibrary.visibility = View.GONE
                tvSearchNotFound.text = getString(R.string.result_not_found2, query)
            }
        } else {
            binding?.apply {
                containerSearchNotFound.visibility = View.GONE
                rvStoryLibrary.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}