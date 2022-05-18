package com.bangkit.alpaca.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding?.btnProcessRegistration?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_login_from_registration -> v.findNavController()
                .navigate(R.id.action_registrationFragment_to_loginFragment2)
            R.id.btn_process_registration -> registrationHandler()
        }
    }

    private fun registrationHandler() {
        if (!isFormValid()) return
        Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun isFormValid(): Boolean {
        val name = binding?.edtNameRegistration?.text.toString()
        val email = binding?.edtEmailRegistration?.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val password = binding?.edtPasswordRegistration?.text.toString()

        binding?.tilNameRegistration?.apply {
            if (name.isEmpty()) {
                isErrorEnabled = true
                error = getString(R.string.error_empty_name)
            } else {
                isErrorEnabled = false
                error = null
            }

        }

        binding?.tilEmailRegistration?.apply {
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

        binding?.tilPasswordRegistration?.apply {
            if (password.isEmpty()) {
                isErrorEnabled = true
                error = getString(R.string.error_empty_password)
            } else {
                isErrorEnabled = false
                error = null
            }
        }

        return binding?.tilNameRegistration?.isErrorEnabled == false &&
                binding?.tilEmailRegistration?.isErrorEnabled == false &&
                binding?.tilPasswordRegistration?.isErrorEnabled == false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}