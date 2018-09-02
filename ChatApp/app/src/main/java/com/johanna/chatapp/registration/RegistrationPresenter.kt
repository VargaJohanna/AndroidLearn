package com.johanna.chatapp.registration

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.Database

class RegistrationPresenter constructor(private val registrationView: RegistrationView) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createAccount(email: String, password: String, displayName: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid.toString()
                        val databaseReference = FirebaseDatabase.getInstance().reference.child(Database.usersNode).child(userId)
                        updateDatabase(displayName, databaseReference)
                    }
                }
                .addOnFailureListener {
                    registrationView.registrationError(it.message.toString())
                }
    }

    private fun updateDatabase(displayName: String, databaseReference: DatabaseReference) {
        val userObject = HashMap<String, String>()
        userObject.put("display_name", displayName)
        userObject.put("status", "Hello")
        userObject.put("image", "default")
        userObject.put("thumb_image", "default")
        databaseReference.setValue(userObject).addOnCompleteListener { task: Task<Void> ->
            updateDatabaseResult(task.isSuccessful, displayName)
        }
    }

    private fun updateDatabaseResult(success: Boolean, displayName: String) {
        if (success) {
            registrationView.updateDatabaseSuccess(displayName)
        } else {
            registrationView.updateDatabaseFail()
        }
    }
}