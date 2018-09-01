package com.johanna.chatapp.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.dashboard.DashboardActivity
import com.johanna.chatapp.activities.login.LoginActivity
import com.johanna.chatapp.activities.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
        mainPresenter.startAuthStateListener()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.stopAuthStateListener()
    }

    fun onUserLoggedIn() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    fun onUserNotLoggedIn() {
        Toast.makeText(this, "Need to login", Toast.LENGTH_LONG).show()
    }
}
