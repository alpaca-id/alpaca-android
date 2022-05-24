package com.bangkit.alpaca.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.FragmentLoginBinding
import com.bangkit.alpaca.ui.main.MainActivity
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showError
import com.bangkit.alpaca.utils.showToastMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val loginViewModel: LoginViewModel by viewModels()
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        mAuth = Firebase.auth
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        loginResult()
    }

    private fun setupAction() {
        binding?.btnToRegistrationFromLogin?.setOnClickListener(this)
        binding?.btnToForgotPassword?.setOnClickListener(this)
        binding?.btnProcessLogin?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_registration_from_login -> v.findNavController()
                .navigate(R.id.action_loginFragment_to_registrationFragment)
            R.id.btn_to_forgot_password -> v.findNavController()
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            R.id.btn_process_login -> loginHandler()
        }
    }

    private fun loginHandler() {
        if (!isFormValid()) return

        val email = binding?.edtEmailLogin?.text.toString()
        val password = binding?.edtPasswordLogin?.text.toString()

        loginViewModel.loginUser(email, password)
    }

    private fun loginResult() {
        loginViewModel.result.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is Result.Loading -> LoadingDialog.displayLoading(
                        requireContext(),
                        false
                    )
                    is Result.Success -> {
                        LoadingDialog.hideLoading()
                        if (result.data) {
                            val user = mAuth.currentUser
                            updateUI(user)
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
        val email = binding?.edtEmailLogin?.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val password = binding?.edtPasswordLogin?.text.toString()

        binding?.tilEmailLogin?.apply {
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

        binding?.tilPasswordLogin?.apply {
            if (password.isEmpty()) {
                showError(true, getString(R.string.error_empty_password))
            } else {
                showError(false)
            }
        }

        binding?.tilPasswordLogin?.apply {
            if (password.length < 6) {
                showError(true, getString(R.string.error_lenght_password))
            } else {
                showError(false)
            }
        }

        return binding?.tilEmailLogin?.isErrorEnabled == false &&
                binding?.tilPasswordLogin?.isErrorEnabled == false
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val mainIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(mainIntent)
            activity?.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}