package com.bangkit.alpaca.ui.wordorder.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.alpaca.databinding.FragmentLevelBinding
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.ui.adapter.WordOrderLevelAdapter
import com.bangkit.alpaca.utils.DataDummy
import com.bangkit.alpaca.utils.showToastMessage

class WordLevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding
    private val wordOrderLevelAdapter by lazy { WordOrderLevelAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupLevel()
        setupAction()
    }

    private fun setupToolbar() {
        binding?.toolbarWordOrder?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupLevel() {
        wordOrderLevelAdapter.submitList(DataDummy.providesWordOrderLevel())
        binding?.rvWordOrder?.apply {
            adapter = wordOrderLevelAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setupAction() {
        wordOrderLevelAdapter.setonItemClickCallback(object :
            WordOrderLevelAdapter.OnItemClickCallback {
            override fun onItemClicked(wordLevel: WordLevel) {
                wordLevel.level.toString().showToastMessage(requireContext())
            }
        })
    }
}