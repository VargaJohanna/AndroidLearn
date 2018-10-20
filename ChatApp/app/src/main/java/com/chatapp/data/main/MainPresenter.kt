package com.chatapp.data.main

import com.chatapp.ui.main.MainView
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

    fun onStart() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun onStop() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

}