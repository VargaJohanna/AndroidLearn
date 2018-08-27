package com.johanna.chatapp.activities.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.models.FriendlyMessage

class ChatActivity : AppCompatActivity() {
    companion object {
        const val userId: String = "id"
        const val userName: String = "name"
        const val userStatus: String = "userStatus"
        const val profileImage: String = "imageLink"
    }

    val mFireBaseUser = FirebaseAuth.getInstance().currentUser
    val mLinearLayoutManager = LinearLayoutManager(this)
    val mFirebaseDatabaseRef = FirebaseDatabase.getInstance().reference
    lateinit var currentUserId: String
    lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        currentUserId = intent.extras.getString(userId)
        mLinearLayoutManager.stackFromEnd = true

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                        .setQuery(mFirebaseDatabaseRef.child("messages"), FriendlyMessage::class.java)
                        .build()) {
            override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: FriendlyMessage) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message, parent, false)

                return MessageViewHolder(view)
            }
        }
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(friendlyMessage: FriendlyMessage) {

        }
    }
}
