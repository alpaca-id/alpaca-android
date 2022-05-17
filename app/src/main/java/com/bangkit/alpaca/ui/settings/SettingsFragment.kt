package com.bangkit.alpaca.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding?.apply {
            btnUserProfile.setOnClickListener(this@SettingsFragment)
            btnCustomisationProfile.setOnClickListener(this@SettingsFragment)
            btnLogout.setOnClickListener(this@SettingsFragment)
            btnAboutApps.setOnClickListener(this@SettingsFragment)
            btnPrivacyTerms.setOnClickListener(this@SettingsFragment)
            btnUserTerms.setOnClickListener(this@SettingsFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_user_profile -> navigateToUserProfile()
            R.id.btn_customisation_profile -> navigateToCustomisation()
            R.id.btn_logout -> logoutHandler()
            R.id.btn_about_apps -> navigateToAboutApps()
            R.id.btn_privacy_terms -> navigateToPrivacyTerms()
            R.id.btn_user_terms -> navigateToUserTerms()
        }
    }

    private fun navigateToUserProfile() {
        Toast.makeText(requireContext(), "Profil Pengguna", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCustomisation() {
        Toast.makeText(requireContext(), "Kustomisasi Teks", Toast.LENGTH_SHORT).show()
    }

    private fun logoutHandler() {
        Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAboutApps() {
        Toast.makeText(requireContext(), "Tentang Aplikasi", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToPrivacyTerms() {
        Toast.makeText(requireContext(), "Ketentuan Privasi", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToUserTerms() {
        Toast.makeText(requireContext(), "Ketentuan Pengguna", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}