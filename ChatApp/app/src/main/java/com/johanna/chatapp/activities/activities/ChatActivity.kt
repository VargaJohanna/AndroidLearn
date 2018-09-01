package com.johanna.chatapp.activities.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.squareup.picasso.Picasso
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
    val currentUser = mFireBaseUser?.uid
    lateinit var currentUserName:String
    lateinit var currentUserStatus:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        otherUserId = intent.extras.getString(userId)
        val otherUserStatus = intent.extras.getString(userStatus)
        mLinearLayoutManager.stackFromEnd = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val child = mFirebaseDatabaseRef.child("Users").child(currentUser!!).child("messages").child(otherUserId)

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                        .setQuery(child, FriendlyMessage::class.java)
                        .build()) {
            override fun onBindViewHolder(holder: MessageViewHolder, position: Int, friendlyMessage: FriendlyMessage) {

                holder.bindView(friendlyMessage)
                val isMe: Boolean = friendlyMessage.id.equals(currentUser)

                if (isMe) {
                    // Me to the right
                    holder.profileImageViewRight.visibility = View.VISIBLE
                    holder.profileImageViewLeft.visibility = View.GONE
                    holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                    holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                    holder.messengerNameTextView.text = getString(R.string.myMessage)

                    val imageUrl = "https://api.adorable.io/avatars/145/$currentUserStatus.png"
                    Picasso.with(holder.profileImageViewRight.context)
                            .load(imageUrl)
                            .placeholder(R.drawable.profile_img)
                            .into(holder.profileImageViewRight)

                } else {
                    // Image to the left
                    holder.profileImageViewRight.visibility = View.GONE
                    holder.profileImageViewLeft.visibility = View.VISIBLE
                    holder.messageTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
                    holder.messengerNameTextView.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)

                    //Get name from database (and image would be here too)
                    mFirebaseDatabaseRef.child("Users").child(otherUserId).child("messages")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(data: DatabaseError) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_message, parent, false)

                return MessageViewHolder(view)
            }
        }

        messageRecyclerView.layoutManager = mLinearLayoutManager
        messageRecyclerView.adapter = mFirebaseAdapter
        sendButton.isEnabled = false
        mFirebaseDatabaseRef.child("Users").child(currentUser)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(data: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        currentUserName = data.child("display_name").value.toString()
                        currentUserStatus = data.child("status").value.toString()
                        sendButton.isEnabled = true
                    }
                })

        sendButton.setOnClickListener {
            if (intent.extras.get(userName).toString().equals("").not()) {
                val friendlyMessage = FriendlyMessage(
                        currentUser,
                        messageEdit.text.toString().trim(),
                        currentUserName.trim())

                mFirebaseDatabaseRef.child("Users").child(currentUser).child("messages").child(otherUserId).push().setValue(friendlyMessage)
                mFirebaseDatabaseRef.child("Users").child(otherUserId).child("messages").child(currentUser).push().setValue(friendlyMessage)

                messageEdit.setText("")
                val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
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
