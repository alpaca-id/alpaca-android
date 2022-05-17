package com.bangkit.alpaca.ui.processing.loading

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.alpaca.databinding.FragmentLoadingBinding
import kotlinx.coroutines.Runnable

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Animate cancel button alpha after 4000ms
        Runnable {
            binding.btnCancel.apply {
                isEnabled = true
                ObjectAnimator.ofFloat(this, View.ALPHA, 1f).apply {
                    duration = 400
                    start()
                }
            }
        }.also {
            binding.btnCancel.postDelayed(it, 4000)
        }

        // SetOnClickListener Handler
        binding.apply {
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