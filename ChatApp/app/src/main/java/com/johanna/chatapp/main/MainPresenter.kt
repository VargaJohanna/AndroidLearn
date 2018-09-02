package com.johanna.chatapp.main

import com.google.firebase.auth.FirebaseAuth

class MainPresenter constructor(private val mainView: MainView){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    fun fetchUserState() {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                mainView.onUserLoggedIn()
            } else {
                mainView.onUserNotLoggedIn()
            }
        }
    }

    fun startAuthStateListener() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun stopAuthStateListener() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

}