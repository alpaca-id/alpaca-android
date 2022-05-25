package com.bangkit.alpaca.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.ActivityProfileBinding
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showError
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var oldEmail: String
    private lateinit var oldName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAction()
        initUser()
        updateResult()
    }

    private fun setupToolbar() {
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupAction() {
        binding.btnSaveProfile.setOnClickListener { updateUser() }
    }

    private fun initUser() {
        profileViewModel.getUserData()
        profileViewModel.user.observe(this) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(this, false)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    with(binding) {
                        edtNameProfile.setText(result.data.name)
                        edtEmailProfile.setText(result.data.email)
                        oldName = result.data.name
                        oldEmail = result.data.email
                    }
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(this)
                }
            }
        }
    }

    private fun updateUser() {
        val newName = binding.edtNameProfile.text.toString()
        val newEmail = binding.edtEmailProfile.text.toString()

        if (!isFormValid()) return

        if (oldName == newName && oldEmail == newEmail) {
            getString(R.string.no_change).showToastMessage(this)
            return
        }

        showConfirmation(newEmail, newName)
    }

    private fun updateResult() {
        profileViewModel.result.observe(this) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(this, false)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    if (result.data){
                        getString(R.string.saved).showToastMessage(this)
                    }
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(this)
                }
            }
        }
    }



    private fun showConfirmation(email: String, name: String) {
        val inflate = layoutInflater
        val dialogLayout = inflate.inflate(R.layout.view_password_confirmation, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.edt_confirmation_password)

        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.save_update))
            .setMessage(getString(R.string.password_confirmation))
            .setView(dialogLayout)
            .setPositiveButton(getString(R.string.label_process)) { _, _ ->
                val password = editText.text.toString()
                profileViewModel.updateUserData(email, name, password)
            }
            .setNegativeButton(getString(R.string.label_cancel)) { _, _ -> }
            .show()

        builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    private fun isFormValid(): Boolean {
        val name = binding.edtNameProfile.text.toString()
        val email = binding.edtEmailProfile.text.toString()
        val isEmailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        binding.tilNameProfile.apply {
            if (name.isEmpty()) {
                showError(true, getString(R.string.error_empty_name))
            } else {
                showError(false)
            }
        }

        binding.tilEmailProfile.apply {
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

        return !binding.tilNameProfile.isErrorEnabled &&
                !binding.tilEmailProfile.isErrorEnabled
    }
}