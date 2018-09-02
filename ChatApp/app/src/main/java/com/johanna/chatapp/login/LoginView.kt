package com.johanna.chatapp.login

interface LoginView {
    fun loginSuccessful(email: String)
    fun loginFail()
}