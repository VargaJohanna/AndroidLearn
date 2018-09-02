package com.johanna.chatapp.activities.chat

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.models.FriendlyMessage
import com.squareup.picasso.Picasso

class ChatAdapter (
        private val currentUserId: String,
        private val currentUserStatus: String,
        private val otherUserId: String,
        private val otherUserStatus: String,
        private val context: ChatActivity)
    : FirebaseRecyclerAdapter<FriendlyMessage, ChatActivity.MessageViewHolder>(
        FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                .setQuery(FirebaseDatabase.getInstance().reference
                        .child("Users")
                        .child(currentUserId)
                        .child("messages")
                        .child(otherUserId)
                        , FriendlyMessage::class.java)
                .build()) {
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override fun onBindViewHolder(holder: ChatActivity.MessageViewHolder, position: Int, friendlyMessage: FriendlyMessage) {
        holder.bindView(friendlyMessage = friendlyMessage)
        val isMe: Boolean = friendlyMessage.id.equals(currentUserId)

        if (isMe) {
            holder.profileImageViewRight.visibility = View.VISIBLE
            holder.profileImageViewLeft.visibility = View.GONE
            holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
            holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
            holder.messengerNameTextView.text = context.getString(R.string.myMessage)

            val imageUrl = "https://api.adorable.io/avatars/145/$currentUserStatus.png"
            Picasso.with(holder.profileImageViewRight.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.profile_img)
                    .into(holder.profileImageViewRight)

        } else {
            holder.profileImageViewRight.visibility = View.GONE
            holder.profileImageViewLeft.visibility = View.VISIBLE
            holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
            holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)

            databaseReference.child("Users").child(otherUserId).child("messages")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(data: DatabaseError) {
                            Log.e("Error", data.message)
                        }

                        override fun onDataChange(data: DataSnapshot) {
                            val imageUrl = "https://api.adorable.io/avatars/145/$otherUserStatus.png"
                            holder.messengerNameTextView.text = "${friendlyMessage.name}'s message:"

                            Picasso.with(holder.profileImageViewLeft.context)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.profile_img)
                                    .into(holder.profileImageViewLeft)
                        }

                    })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatActivity.MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)

        return ChatActivity.MessageViewHolder(view)
    }
}