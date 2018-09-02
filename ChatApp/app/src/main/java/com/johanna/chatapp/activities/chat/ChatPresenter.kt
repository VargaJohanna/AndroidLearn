package com.johanna.chatapp.activities.chat

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johanna.chatapp.activities.models.FriendlyMessage
import kotlinx.android.synthetic.main.activity_chat.*

class ChatPresenter constructor(private val chatView: ChatView) {
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    lateinit var currentUserName: String

    fun getCurrentUser(): String {
        return currentUser
    }

    fun userIsReady(currentUserStatus: String, otherUserId: String, otherUserStatus: String, context: ChatActivity) {
        chatView.createChatAdapter(currentUser, currentUserStatus, otherUserId, otherUserStatus, context)
    }

    fun fetchUserDetails(otherUserId: String, otherUserStatus: String, context: ChatActivity) {
        databaseReference.child("Users").child(currentUser)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(data: DatabaseError) {
                        Log.e("Error", data.message)
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        currentUserName = data.child("display_name").value.toString()
                        val currentUserStatus = data.child("status").value.toString()
                        chatView.enableSendButton()
                        userIsReady(currentUserStatus, otherUserId, otherUserStatus, context)
                    }
                })
    }

    fun saveMessages(otherUserId: String) {
        val friendlyMessage = FriendlyMessage(
                getCurrentUser(),
                chatView.getMessage(),
                currentUserName.trim())

        databaseReference.child("Users").child(currentUser).child("messages").child(otherUserId).push().setValue(friendlyMessage)
        databaseReference.child("Users").child(otherUserId).child("messages").child(currentUser).push().setValue(friendlyMessage)
    }
}