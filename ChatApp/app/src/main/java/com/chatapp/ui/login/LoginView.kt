package com.chatapp.ui.login

interface LoginView {
    fun loginSuccessful(email: String)
    fun loginFail()
}