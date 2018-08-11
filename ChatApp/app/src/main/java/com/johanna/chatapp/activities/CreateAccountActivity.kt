package com.johanna.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDataBase = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        accountCreateButtonEt.setOnClickListener {
            val displayName = accountDisplayNameEt.text.toString().trim()
            val email = accountEmailEt.text.toString().trim()
            val password = accountPasswordEt.text.toString().trim()

            if (!TextUtils.isEmpty(displayName)
                    && !TextUtils.isEmpty(email)
                    && !TextUtils.isEmpty(password)) {
                createAccount(email, password, displayName)
            } else {
                Toast.makeText(this, "Please fill out the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createAccount(email: String, password: String, displayName: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val currentUser = mAuth.currentUser
                        val userId = currentUser?.uid
                        val userObject = HashMap<String, String>()
                        mDataBase.child("Users").child(userId!!)

                        userObject.put("display_name", displayName)
                        userObject.put("status", "Hello")
                        userObject.put("image", "default")
                        userObject.put("thumb_image", "default")

                        mDataBase.setValue(userObject).addOnCompleteListener { task: Task<Void> ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show()
                                val dashboardActivity = Intent(this, DashboardActivity::class.java)
                                dashboardActivity.putExtra(DashboardActivity.name, displayName)
                                startActivity(dashboardActivity)
                                finish()
                            } else {
                                Toast.makeText(this, "User not created!", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
    }
}

