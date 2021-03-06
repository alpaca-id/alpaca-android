package com.bangkit.alpaca.ui.wordorder.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.FragmentWordLevelBinding
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.ui.adapter.WordOrderLevelAdapter
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WordLevelFragment : Fragment() {

    private var _binding: FragmentWordLevelBinding? = null
    private val binding get() = _binding
    private val wordLevelViewModel: WordLevelViewModel by viewModels()
    private val wordOrderLevelAdapter by lazy { WordOrderLevelAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordLevelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupLevel()
        setupAction()
    }

    private fun setupToolbar() {
        binding?.toolbarWordOrder?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding?.rvWordOrder?.apply {
            adapter = wordOrderLevelAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setupLevel() {
        wordLevelViewModel.getWordLevelData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(requireContext(), false)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    wordOrderLevelAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(requireContext())
                }
            }
        }
    }

    private fun setupAction() {
        wordOrderLevelAdapter.setonItemClickCallback(object :
            WordOrderLevelAdapter.OnItemClickCallback {
            override fun onItemClicked(wordLevel: WordLevel, isLocked: Boolean) {
                if (isLocked) {
                    getString(R.string.level_locked).showToastMessage(requireContext())
                } else {
                    val toWordStage =
                        WordLevelFragmentDirections.actionLevelFragmentToWordStageFragment(wordLevel)
                    binding?.root?.findNavController()?.navigate(toWordStage)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        LoadingDialog.hideLoading()
    }
}