package com.chatapp.data.settings

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.chatapp.data.Database
import com.chatapp.ui.settings.SettingsView

class SettingsPresenter constructor(private val settingsView: SettingsView) {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun fetchUSerDetails() {
        if (userId != null) {
            FirebaseDatabase.getInstance().reference.child(Database.usersNode)
                    .child(userId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userDisplayName = dataSnapshot.child(Database.displayNameNode).value.toString()
                            val userStatusData = dataSnapshot.child(Database.statusNode).value.toString()
                            val userThumbImage = "https://api.adorable.io/avatars/180/$userStatusData.png"
                            settingsView.updateUserDetails(userDisplayName, userStatusData, userThumbImage)
                        }

                        override fun onCancelled(dataErrorSnapshot: DatabaseError) {
                            Log.e("Error", dataErrorSnapshot.message)

                        }
                    })
        }
    }
}