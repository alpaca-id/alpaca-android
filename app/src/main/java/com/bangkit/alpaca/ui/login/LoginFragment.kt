package com.bangkit.alpaca.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentLoginBinding
import com.bangkit.alpaca.ui.AuthenticationActivity
import com.bangkit.alpaca.ui.forgotpassword.ForgotPasswordFragment
import com.bangkit.alpaca.ui.main.MainActivity
import com.bangkit.alpaca.ui.registration.RegistrationFragment

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
            R.id.btn_to_registration_from_login -> moveToRegistration()
            R.id.btn_to_forgot_password -> moveToForgotPassword()
            R.id.btn_process_login -> loginHandler()
        }
    }

    private fun loginHandler() {
        val mainIntent = Intent(requireContext(), MainActivity::class.java)
        startActivity(mainIntent)
        (activity as AuthenticationActivity).finish()
    }

    private fun moveToRegistration() {
        val mRegistrationFragment = RegistrationFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.commit {
            addToBackStack(null)
            replace(
                R.id.auth_container,
                mRegistrationFragment,
                RegistrationFragment::class.java.simpleName
            )
        }
    }

    private fun moveToForgotPassword() {
        val mForgotPasswordFragment = ForgotPasswordFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.commit {
            addToBackStack(null)
            replace(
                R.id.auth_container,
                mForgotPasswordFragment,
                ForgotPasswordFragment::class.java.simpleName
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}