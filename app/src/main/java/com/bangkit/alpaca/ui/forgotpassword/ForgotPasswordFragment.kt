package com.bangkit.alpaca.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding?.btnToLoginFromForgotPassword?.setOnClickListener(this)
        binding?.btnToRegistrationFromForgotPassword?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_login_from_forgot_password -> v.findNavController()
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            R.id.btn_to_registration_from_forgot_password -> v.findNavController()
                .navigate(R.id.action_forgotPasswordFragment_to_registrationFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}