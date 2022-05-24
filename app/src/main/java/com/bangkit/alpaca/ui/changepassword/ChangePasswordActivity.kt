package com.bangkit.alpaca.ui.changepassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityChangePasswordBinding
import com.bangkit.alpaca.utils.showError

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAction()
    }

    private fun setupAction() {
        binding.btnChangePasswordProcess.setOnClickListener { changePassword() }
    }

    private fun changePassword() {
        if (!isFormValid()) return
    }

    private fun isFormValid(): Boolean {
        val currentPassword = binding.edtCurrentPasswordProfile.text.toString()
        val newPassword = binding.edtNewPasswordProfile.text.toString()
        val confirmPassword = binding.edtConfirmPasswordProfile.text.toString()

        binding.tilCurrentPasswordProfile.apply {
            if (currentPassword.length < 6) {
                showError(true, getString(R.string.error_lenght_password))
            } else {
                showError(false)
            }
        }

        binding.tilNewPasswordProfile.apply {
            if (newPassword.length < 6) {
                showError(true, getString(R.string.error_lenght_password))
            } else {
                if (newPassword == currentPassword) {
                    showError(true, getString(R.string.same_password))
                } else {
                    showError(false)
                }
            }
        }

        binding.tilConfirmPasswordProfile.apply {
            if (confirmPassword.isEmpty()) {
                showError(true, getString(R.string.error_empty_password))
            } else {
                if (confirmPassword != newPassword) {
                    showError(true, getString(R.string.password_not_match))
                } else {
                    showError(false)
                }
            }
        }

        return !binding.tilCurrentPasswordProfile.isErrorEnabled &&
                !binding.tilNewPasswordProfile.isErrorEnabled &&
                !binding.tilConfirmPasswordProfile.isErrorEnabled
    }

    private fun setupToolbar() {
        binding.toolbarChangePassword.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}