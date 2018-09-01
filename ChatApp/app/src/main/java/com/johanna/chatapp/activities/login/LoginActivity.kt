package com.johanna.chatapp.activities.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    private val loginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButtonCard.setOnClickListener {
            val email = loginEmailCard.text.toString().trim()
            val password = passwordCard.text.toString().trim()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginPresenter.loginUser(email, password)
            } else {
                Toast.makeText(this, "Sorry, login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun loginSuccessful(email: String) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        dashboardIntent.putExtra(DashboardActivity.name, email)
        startActivity(dashboardIntent)
        finish()
    }

    override fun loginFail() {
        Toast.makeText(this, "Error while log in", Toast.LENGTH_SHORT).show()
        Log.d("Error", "Couldn't log in")
    }
}
