package com.bangkit.alpaca.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding?.btnToRegistrationFromLogin?.setOnClickListener(this)
        binding?.btnToForgotPassword?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_registration_from_login -> moveToRegistration()
            R.id.btn_to_forgot_password -> moveToForgotPassword()
        }
    }

    private fun moveToRegistration() {
        Toast.makeText(requireContext(), "Registration", Toast.LENGTH_SHORT).show()
    }

    private fun moveToForgotPassword() {
        Toast.makeText(requireContext(), "Forgot password", Toast.LENGTH_SHORT).show()
    }
}