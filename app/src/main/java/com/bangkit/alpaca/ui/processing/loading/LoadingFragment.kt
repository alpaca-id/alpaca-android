package com.bangkit.alpaca.ui.processing.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentLoadingBinding
import com.bangkit.alpaca.ui.processing.ProcessingViewModel

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

        processingViewModel.uploadedImageUrl.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loadingFragment_to_confirmationFragment)
        }
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        binding?.apply {
            btnCancel.setOnClickListener {
                // FIXME: Cancel scanning job, and navigate back to the camera
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}