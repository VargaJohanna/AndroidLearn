package com.johanna.chatapp.activities.settings

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsPresenter constructor(private val settingsView: SettingsView) {

    fun fetchUSerDetails() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().reference.child("Users")
                    .child(userId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userDisplayName = dataSnapshot.child("display_name").value.toString()
                            val userStatusData = dataSnapshot.child("status").value.toString()
                            val userThumbImage = "https://api.adorable.io/avatars/145/$userStatusData.png"
                            settingsView.updateUserDetails(userDisplayName, userStatusData, userThumbImage)
                        }

                        override fun onCancelled(dataErrorSnapshot: DatabaseError) {
                            //TODO: Handle it
                        }
                    })
        }
    }
}