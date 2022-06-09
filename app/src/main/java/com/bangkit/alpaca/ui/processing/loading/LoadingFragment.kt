package com.bangkit.alpaca.ui.processing.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentLoadingBinding
import com.bangkit.alpaca.ui.processing.ProcessingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding

    private val processingViewModel: ProcessingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewAction()

        processingViewModel.stringResultPredict.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loadingFragment_to_confirmationFragment)
        }
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        binding?.apply {
            btnCancel.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}