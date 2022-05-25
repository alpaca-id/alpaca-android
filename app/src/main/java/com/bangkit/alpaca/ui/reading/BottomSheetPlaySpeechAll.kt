package com.bangkit.alpaca.ui.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.alpaca.databinding.ModalBottomSheetPlayAllBinding
import com.bangkit.alpaca.ui.adapter.SentencesListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPlaySpeechAll : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetPlayAllBinding? = null
    private val binding get() = _binding
    private val sentencesListAdapter by lazy { SentencesListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetPlayAllBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListSentence()
    }

    private fun setupListSentence() {
        val bundle = arguments?.getStringArrayList(EXTRA_SENTENCES)
        sentencesListAdapter.submitList(bundle)

        binding?.rvSentence?.apply {
            adapter = sentencesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_SENTENCES = "extra sentences"
    }
}