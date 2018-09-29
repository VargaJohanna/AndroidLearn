package com.chatapp.login

import com.chatapp.ui.login.LoginView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter constructor(private val loginView: LoginView) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> -> loginResult(task.isSuccessful, email) }
    }

    private fun loginResult(success:Boolean, email: String){
        if (success) {
            loginView.loginSuccessful(email)
        } else {
            loginView.loginFail()
        }
    }
}