package com.johanna.chatapp.chat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johanna.chatapp.models.FriendlyMessage

class ChatPresenter constructor(private val chatView: ChatView) {
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    lateinit var currentUserName: String

    fun userIsReady(currentUserStatus: String, otherUserId: String, otherUserStatus: String, context: ChatActivity) {
        chatView.createChatAdapter(currentUserId, currentUserStatus, otherUserId, otherUserStatus, context)
    }

    fun fetchUserDetails(otherUserId: String, otherUserStatus: String, context: ChatActivity) {
        databaseReference.child("Users").child(currentUserId)
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
                currentUserId,
                chatView.getMessage(),
                currentUserName.trim())

        databaseReference.child("Users").child(currentUserId).child("messages").child(otherUserId).push().setValue(friendlyMessage)
        databaseReference.child("Users").child(otherUserId).child("messages").child(currentUserId).push().setValue(friendlyMessage)
    }
}