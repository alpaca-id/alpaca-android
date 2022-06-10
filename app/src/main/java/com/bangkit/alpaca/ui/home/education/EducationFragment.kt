package com.bangkit.alpaca.ui.home.education

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentEducationBinding
import com.bangkit.alpaca.ui.wordorder.WordOrderActivity
import com.bangkit.alpaca.utils.animateVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Runnable

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EducationFragment : Fragment() {

    private var _binding: FragmentEducationBinding? = null
    private val binding get() = _binding

    private val viewModel: EducationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEducationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.flashcards.observe(viewLifecycleOwner) { flashcards ->

            binding?.apply {
                var index = 0
                tvFlashcardTitle.text = flashcards[index].title
                tvFlashcardMessage.text = flashcards[index].message
                tvFlashcardCounter.text =
                    getString(R.string.counter_flashcard, index + 1, flashcards.size)

                btnChange.setOnClickListener {
                    index = if (index < flashcards.size - 1) index + 1 else 0

                    binding?.apply {
                        val duration = 400L
                        tvFlashcardTitle.animateVisibility(false, duration)
                        tvFlashcardMessage.animateVisibility(false, duration)
                        tvFlashcardCounter.animateVisibility(false, duration)

                        Runnable {
                            tvFlashcardTitle.text = flashcards[index].title
                            tvFlashcardMessage.text = flashcards[index].message
                            tvFlashcardCounter.text =
                                getString(R.string.counter_flashcard, index + 1, flashcards.size)
                            tvFlashcardTitle.animateVisibility(true, duration)
                            tvFlashcardMessage.animateVisibility(true, duration)
                            tvFlashcardCounter.animateVisibility(true, duration)


                        }.also {
                            tvFlashcardTitle.postDelayed(it, duration)
                        }
                    }
                }
            }
        }

        binding?.cardWordGame?.setOnClickListener {
            val wordOrderIntent = Intent(requireContext(), WordOrderActivity::class.java)
            startActivity(wordOrderIntent)
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
}