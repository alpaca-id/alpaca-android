package com.bangkit.alpaca.ui.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding?.btnProcessNext?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_login_from_forgot_password -> v.findNavController()
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            R.id.btn_to_registration_from_forgot_password -> v.findNavController()
                .navigate(R.id.action_forgotPasswordFragment_to_registrationFragment)
            R.id.btn_process_next -> forgotPasswordHandler()
        }
    }

    private fun forgotPasswordHandler() {
        if (!isFormValid()) return
        Toast.makeText(requireContext(), "Diproses", Toast.LENGTH_SHORT).show()
    }

    private fun isFormValid(): Boolean {
        val email = binding?.edtEmailForgotPassword?.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        binding?.tilEmailForgotPassword?.apply {
            if (email.isEmpty()) {
                isErrorEnabled = true
                error = getString(R.string.error_empty_email)
            } else {
                if (!isEmailFormatValid) {
                    error = getString(R.string.error_email_format)
                } else {
                    isErrorEnabled = false
                    error = null
                }
            }
        }
        return binding?.tilEmailForgotPassword?.isErrorEnabled == false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}