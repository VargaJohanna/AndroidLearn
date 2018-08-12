package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDataBase = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButtonCard.setOnClickListener {
            val email = loginEmailCard.text.toString().trim()
            val password = passwordCard.text.toString().trim()

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Sorry, login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    task: Task<AuthResult> ->
                    if(task.isSuccessful) {
                        val dashboardIntent = Intent(this, DashboardActivity::class.java)
                        dashboardIntent.putExtra(DashboardActivity.name, email)
                        startActivity(dashboardIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error while log in", Toast.LENGTH_SHORT).show()
                        Log.d("Error", "Couldn't log in")
                    }
                }
    }
}
