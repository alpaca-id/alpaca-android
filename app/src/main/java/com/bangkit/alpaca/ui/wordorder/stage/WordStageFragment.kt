package com.bangkit.alpaca.ui.wordorder.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentWordStageBinding
import com.bangkit.alpaca.model.AnswerButton
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.model.WordStage
import com.bangkit.alpaca.ui.adapter.AnswerButtonAdapter

class WordStageFragment : Fragment() {

    private var _binding: FragmentWordStageBinding? = null
    private val binding get() = _binding
    private val args: WordStageFragmentArgs by navArgs()
    private val answerButtonAdapter by lazy { AnswerButtonAdapter() }
    val answer = arrayListOf<String>()
    private lateinit var wordLevel: WordLevel
    private lateinit var wordStage: List<WordStage>
    private lateinit var currentStage: WordStage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordStageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordLevel = args.wordLevel
        wordStage = wordLevel.wordStages
        currentStage = getCurrentStage(wordStage)

        setupToolbar()
        setupStage()
        setupAnswerButton()
        setupAction()
    }

    private fun setupToolbar() {
        binding?.toolbarStages?.apply {
            title = getString(R.string.title_level, wordLevel.level.toString())
            setNavigationOnClickListener {
                binding?.root?.findNavController()
                    ?.navigate(R.id.action_wordStageFragment_to_wordLevelFragment)
            }
        }
    }

    private fun setupStage() {
        binding?.labelCurrentStage?.text =
            getString(R.string.stages, currentStage.stage.toString(), wordStage.size.toString())
        binding?.tvCurrentWord?.text = currentStage.word
    }

    private fun setupAnswerButton() {
        val answerButton = convertCurrentWordToListChar(currentStage)
        answerButtonAdapter.submitList(answerButton)
        binding?.rvAnswerChar?.apply {
            adapter = answerButtonAdapter
        }
    }

    private fun setupAction() {
        nextStage()
        previousStage()
        answerButtonClick()
        checkAnswer()
    }

    private fun nextStage() {
        binding?.btnNextStage?.apply {
            visibility = if (currentStage.stage == wordStage.size || !currentStage.isComplete) {
                View.GONE
            } else {
                View.VISIBLE
            }

            setOnClickListener {
                moveStage(wordStage[currentStage.stage])
            }
        }
    }

    private fun previousStage() {
        binding?.btnPreviousStage?.apply {
            visibility = if (currentStage.stage == 1) {
                View.GONE
            } else {
                View.VISIBLE
            }

            setOnClickListener {
                moveStage(wordStage[currentStage.stage - 2])
            }
        }
    }

    private fun moveStage(newStage: WordStage) {
        currentStage = newStage
        binding?.edtAnswer?.setText("")
        answer.clear()

        nextStage()
        previousStage()
        setupStage()
        setupAnswerButton()
    }

    private fun answerButtonClick() {
        answerButtonAdapter.setOnItemClickCallback(object :
            AnswerButtonAdapter.OnItemClickCallback {
            override fun onItemClicked(answerButton: AnswerButton, position: Int) {
                if (!answerButton.isClicked) {
                    answer.remove(answerButton.char)
                } else {
                    answer.add(answerButton.char)
                }
                binding?.edtAnswer?.setText(answer.joinToString(""))
            }
        })
    }

    private fun checkAnswer() {
        binding?.btnCheck?.setOnClickListener {
            val userAnswer = binding?.edtAnswer?.text?.toString()

            if (currentStage.word == userAnswer) {
                val modalBottomSheetRightAnswer = BottomSheetRightAnswer()
                modalBottomSheetRightAnswer.show(
                    parentFragmentManager,
                    BottomSheetRightAnswer::class.java.simpleName
                )
            } else {
                val modalBottomSheetWrongAnswer = BottomSheetWrongAnswer()
                modalBottomSheetWrongAnswer.show(
                    parentFragmentManager,
                    BottomSheetWrongAnswer::class.java.simpleName
                )
            }
        }
    }

    private fun getCurrentStage(wordStage: List<WordStage>): WordStage {
        for (stage in wordStage) {
            if (!stage.isComplete) {
                return stage
            }
        }
        return wordStage[0]
    }

    private fun convertCurrentWordToListChar(currentStage: WordStage): List<AnswerButton> {
        val answerButton = arrayListOf<AnswerButton>()
        val word = currentStage.word
        val chars = word.toCharArray().apply { sort() }

        for (char in chars) {
            val data = AnswerButton(char.toString(), false)
            answerButton.add(data)
        }

        return answerButton
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}