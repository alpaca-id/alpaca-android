package com.bangkit.alpaca.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.bangkit.alpaca.R
import com.bangkit.alpaca.ui.login.LoginFragment

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val mLoginFragment = LoginFragment()
        val mFragmentManager = supportFragmentManager
        mFragmentManager.commit {
            add(R.id.auth_container, mLoginFragment, LoginFragment::class.java.simpleName)
        }
    }
}