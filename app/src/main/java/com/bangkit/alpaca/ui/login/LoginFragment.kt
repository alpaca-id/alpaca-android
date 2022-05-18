package com.bangkit.alpaca.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bangkit.alpaca.MainActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentLoginBinding
import com.bangkit.alpaca.ui.AuthenticationActivity

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
        val mainIntent = Intent(requireContext(), MainActivity::class.java)
        startActivity(mainIntent)
        (activity as AuthenticationActivity).finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}