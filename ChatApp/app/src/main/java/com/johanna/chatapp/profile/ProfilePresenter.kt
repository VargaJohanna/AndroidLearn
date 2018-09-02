package com.johanna.chatapp.profile

import android.util.Log
import com.google.firebase.database.*
import com.johanna.chatapp.Database

class ProfilePresenter constructor(private val profileActivity: ProfileActivity) {
    private val userReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
                .child(Database.usersNode)
                .child(profileActivity.userIdValue)
    }

    fun setUpProfile() {
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val displayName = dataSnapshot.child("display_name").value.toString()
                val status = dataSnapshot.child("status").value.toString()
                val image = "https://api.adorable.io/avatars/260/$status.png"

                profileActivity.linkProfileDetails(displayName, status, image)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Error", databaseError.message)

            }
        })
    }

}