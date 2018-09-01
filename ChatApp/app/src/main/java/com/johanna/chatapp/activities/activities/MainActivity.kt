 package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity() {
     private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
     private var mAuthListener: FirebaseAuth.AuthStateListener? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuthListener = FirebaseAuth.AuthStateListener {
            firebaseAuth: FirebaseAuth ->
            val user = firebaseAuth.currentUser

            if(user != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Need to login", Toast.LENGTH_LONG).show()
            }
        }

        createAccountButton.setOnClickListener{
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

     override fun onStart() {
         super.onStart()
         mAuth.addAuthStateListener(mAuthListener!!)
     }

     override fun onStop() {
         super.onStop()
         if(mAuthListener != null) {
             mAuth.removeAuthStateListener(mAuthListener!!)
         }
     }
}
