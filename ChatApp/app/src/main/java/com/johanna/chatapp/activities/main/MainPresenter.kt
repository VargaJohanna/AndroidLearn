package com.johanna.chatapp.activities.main

import com.google.firebase.auth.FirebaseAuth

class MainPresenter constructor(private val mainActivity: MainActivity){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var authStateListener: FirebaseAuth.AuthStateListener

    fun fetchUserState() {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                mainActivity.onUserLoggedIn()
            } else {
                mainActivity.onUserNotLoggedIn()
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