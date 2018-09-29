package com.chatapp.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.chatapp.R
import com.chatapp.dashboard.DashboardActivity
import com.chatapp.login.LoginActivity
import com.chatapp.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.fetchUserState()

        createAccountButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onStop()
    }

    override fun onUserLoggedIn() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun onUserNotLoggedIn() {
        Toast.makeText(this, getString(R.string.needToLogin), Toast.LENGTH_LONG).show()
    }
}
