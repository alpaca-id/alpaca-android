package com.bangkit.alpaca.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.alpaca.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

}