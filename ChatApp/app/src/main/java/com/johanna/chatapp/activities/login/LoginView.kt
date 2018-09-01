package com.johanna.chatapp.activities.login

interface LoginView {
    fun loginSuccessful(email: String)
    fun loginFail()
}