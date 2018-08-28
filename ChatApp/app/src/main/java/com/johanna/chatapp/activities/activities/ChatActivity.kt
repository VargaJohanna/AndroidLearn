package com.johanna.chatapp.activities.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.models.FriendlyMessage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*

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
    lateinit var otherUserId: String
    lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        otherUserId = intent.extras.getString(userId)
        mLinearLayoutManager.stackFromEnd = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                        .setQuery(mFirebaseDatabaseRef.child("messages"), FriendlyMessage::class.java)
                        .build()) {
            override fun onBindViewHolder(holder: MessageViewHolder, position: Int, friendlyMessage: FriendlyMessage) {

                holder.bindView(friendlyMessage)

                val currentUser = mFireBaseUser?.uid
                val isMe: Boolean = friendlyMessage.id.equals(currentUser)

                if (isMe) {
                    // Me to the right
                    holder.profileImageViewRight.visibility = View.VISIBLE
                    holder.profileImageViewLeft.visibility = View.GONE
                    holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                    holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                    holder.messengerNameTextView.text = "me"

                } else {
                    // Image to the left
                    holder.profileImageViewRight.visibility = View.GONE
                    holder.profileImageViewLeft.visibility = View.VISIBLE
                    holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
                    holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)

                    //Get name from database (and image would be here too)
                    mFirebaseDatabaseRef.child("Users").child(userId)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(data: DatabaseError) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onDataChange(data: DataSnapshot) {
                                    val displayName = data.child("display_name")
                                    holder.messengerNameTextView.text = displayName.value.toString()
                                }

                            })
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message, parent, false)

                return MessageViewHolder(view)
            }
        }

        messageRecyclerView.layoutManager = mLinearLayoutManager
        messageRecyclerView.adapter = mFirebaseAdapter

        sendButton.setOnClickListener {
            if (!intent.extras.get(userName).toString().equals("")) {
                val currentUserName = intent.extras.get(userName)
                val mCurrentUserId = mFireBaseUser?.uid

                val friendlyMessage = FriendlyMessage(mCurrentUserId.toString(),
                        messageEdit.text.toString().trim(),
                        currentUserName.toString().trim())

                mFirebaseDatabaseRef.child("messages").push().setValue(friendlyMessage)
                messageEdit.setText("")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
    }


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var messageTextView: TextView
        lateinit var messengerNameTextView: TextView
        lateinit var profileImageViewLeft: CircleImageView
        lateinit var profileImageViewRight: CircleImageView

        fun bindView(friendlyMessage: FriendlyMessage) {
            messageTextView = itemView.findViewById(R.id.messageTextView)
            messengerNameTextView = itemView.findViewById(R.id.nameOfMessenger)
            profileImageViewLeft = itemView.findViewById(R.id.messengerImageViewLeft)
            profileImageViewRight = itemView.findViewById(R.id.messengerImageViewRight)

            messageTextView.text = friendlyMessage.text
            messengerNameTextView.text = friendlyMessage.name

        }
    }


}
