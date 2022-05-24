package com.bangkit.alpaca.ui.profile

import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.databinding.ActivityProfileBinding
import com.bangkit.alpaca.utils.LoadingDialog
import com.bangkit.alpaca.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        initUser()
        updateUser()
    }

    private fun initUser() {
        profileViewModel.getUserData()
        profileViewModel.user.observe(this) { result ->
            when (result) {
                is Result.Loading -> LoadingDialog.displayLoading(this, true)
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                    with(binding) {
                        edtNameProfile.setText(result.data.name)
                        edtEmailProfile.setText(result.data.email)
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
        binding.btnSaveProfile.setOnClickListener {
            val email = binding.edtEmailProfile.text.toString()
            val name = binding.edtNameProfile.text.toString()
            showConfirmation(email, name)
        }
        profileViewModel.result.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    LoadingDialog.displayLoading(this, true)
                }
                is Result.Success -> {
                    LoadingDialog.hideLoading()
                }
                is Result.Error -> {
                    LoadingDialog.hideLoading()
                    result.error.showToastMessage(this)
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun showConfirmation(email: String, name: String) {
        val inflate = layoutInflater
        val dialogLayout = inflate.inflate(R.layout.view_password_confirmation, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.edt_confirmation_password)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.save_update))
            .setMessage(getString(R.string.password_confirmation))
            .setView(dialogLayout)
            .setPositiveButton(getString(R.string.label_process)) { _, _ ->
                val password = editText.text.toString()
                profileViewModel.updateUserData(email, name, password)
            }
            .setNegativeButton(getString(R.string.label_cancel)) { _, _ -> }
            .show()
    }
}