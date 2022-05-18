package com.bangkit.alpaca.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(), View.OnClickListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding?.btnToLoginFromRegistration?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_login_from_registration -> v.findNavController()
                .navigate(R.id.action_registrationFragment_to_loginFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}