package com.bangkit.alpaca.ui.auth.changepassword

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.ActivityChangePasswordBinding
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showError
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAction()
        changePasswordResult()
    }

    private fun setupAction() {
        binding.btnChangePasswordProcess.setOnClickListener { changePassword() }
    }

    private fun changePassword() {
        if (!isFormValid()) return
        val currentPassword = binding.edtCurrentPasswordProfile.text.toString()
        val newPassword = binding.edtNewPasswordProfile.text.toString()

        changePasswordViewModel.updatePassword(currentPassword, newPassword)
    }

    private fun changePasswordResult() {
        changePasswordViewModel.result.observe(this) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(this, false)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    if (result.data) {
                        getString(R.string.success_change_password).showToastMessage(this)
                        onBackPressed()
                    }
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(this)
                }
            }
        }
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
                showError(true, getString(R.string.error_empty_confirm_password))
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

    override fun onPause() {
        super.onPause()
        LoadingDialog.hideLoading()
    }
}