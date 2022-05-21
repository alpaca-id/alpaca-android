package com.bangkit.alpaca.ui.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentForgotPasswordBinding
import com.bangkit.alpaca.utils.showError

class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        isSendPasswordSuccess()
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

        val email = binding?.edtEmailForgotPassword?.text.toString()
        forgotPasswordViewModel.sendPassword(requireActivity(), email)
    }

    private fun isSendPasswordSuccess() {
        forgotPasswordViewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(
                    requireContext(),
                    "Berhasil mengirim password ke email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Gagal mengirim password ke email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding?.edtEmailForgotPassword?.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        binding?.tilEmailForgotPassword?.apply {
            if (email.isEmpty()) {
                isErrorEnabled = true
                error = getString(R.string.error_empty_email)
                showError(true, getString(R.string.error_empty_email))
            } else {
                if (!isEmailFormatValid) {
                    error = getString(R.string.error_email_format)
                    showError(true, getString(R.string.error_email_format))
                } else {
                    isErrorEnabled = false
                    error = null
                    showError(false)
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