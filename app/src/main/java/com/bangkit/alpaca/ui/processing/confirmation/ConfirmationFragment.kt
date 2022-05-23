package com.bangkit.alpaca.ui.processing.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentConfirmationBinding
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.ui.main.MainActivity
import java.util.*

class ConfirmationFragment : Fragment() {

    private var _binding: FragmentConfirmationBinding? = null
    private val binding get() = _binding!!

    private val confirmationViewModel: ConfirmationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        // Initialize toolbar
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(R.string.simpan_catatan)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewAction()
    }

    /**
     * Handling views' action
     */
    private fun handleViewAction() {
        // Automatically focus on title field and open keyboard
        binding.etTitle.apply {
            requestFocus()

            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etTitle, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.btnSaveResult.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            val story = Story(
                title = title,
                body = content,
                coverPath = null,
                authorName = null,
                createdAt = Calendar.getInstance().timeInMillis
            )

            confirmationViewModel.saveNewStory(story)

            Intent(requireContext(), MainActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}