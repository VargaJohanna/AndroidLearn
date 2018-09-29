package com.chatapp.login

interface LoginView {
    fun loginSuccessful(email: String)
    fun loginFail()
}