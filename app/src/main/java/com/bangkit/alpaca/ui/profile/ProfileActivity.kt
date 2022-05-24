package com.bangkit.alpaca.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private fun setupToolbar() {
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}