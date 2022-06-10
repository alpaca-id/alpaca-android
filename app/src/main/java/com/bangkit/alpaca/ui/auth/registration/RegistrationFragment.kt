package com.bangkit.alpaca.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.FragmentRegistrationBinding
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showError
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding
    private val registrationViewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        registrationResult()
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

        val name = binding?.edtNameRegistration?.text.toString()
        val email = binding?.edtEmailRegistration?.text.toString()
        val password = binding?.edtPasswordRegistration?.text.toString()

        registrationViewModel.registrationUser(name, email, password)
    }

    private fun registrationResult() {
        registrationViewModel.result.observe(requireActivity()) { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is Result.Loading -> LoadingDialog.displayLoading(requireContext(), false)
                    is Result.Success -> {
                        LoadingDialog.hideLoading()
                        if (result.data) {
                            binding?.root?.findNavController()
                                ?.navigate(R.id.action_registrationFragment_to_loginFragment2)
                        }
                    }
                    is Result.Error -> {
                        LoadingDialog.hideLoading()
                        result.error.showToastMessage(requireContext())
                    }
                }
            }
        }
    }

    private fun isFormValid(): Boolean {
        val name = binding?.edtNameRegistration?.text.toString()
        val email = binding?.edtEmailRegistration?.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val password = binding?.edtPasswordRegistration?.text.toString()

        binding?.tilNameRegistration?.apply {
            if (name.isEmpty()) {
                showError(true, getString(R.string.error_empty_name))
            } else {
                showError(false)
            }
        }

        binding?.tilEmailRegistration?.apply {
            if (email.isEmpty()) {
                showError(true, getString(R.string.error_empty_email))
            } else {
                if (!isEmailFormatValid) {
                    showError(true, getString(R.string.error_email_format))
                } else {
                    showError(false)
                }
            }
        }

        binding?.tilPasswordRegistration?.apply {
            if (password.isEmpty()) {
                showError(true, getString(R.string.error_empty_password))
            } else {
                showError(false)
            }
        }

        binding?.tilPasswordRegistration?.apply {
            if (password.length < 6) {
                showError(true, getString(R.string.error_lenght_password))
            } else {
                showError(false)
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

    override fun onPause() {
        super.onPause()
        LoadingDialog.hideLoading()
    }
}