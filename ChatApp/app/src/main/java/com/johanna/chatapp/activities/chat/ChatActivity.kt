package com.johanna.chatapp.activities.chat

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
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

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val linearLayoutManager = LinearLayoutManager(this)
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUser = firebaseUser?.uid.toString()
    lateinit var currentUserName: String
    lateinit var currentUserStatus: String
    var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        linearLayoutManager.stackFromEnd = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val otherUserId = intent.extras.getString(userId)
        val otherUserStatus = intent.extras.getString(userStatus)

        messageRecyclerView.layoutManager = linearLayoutManager
        sendButton.isEnabled = false
        databaseReference.child("Users").child(currentUser)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(data: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        currentUserName = data.child("display_name").value.toString()
                        currentUserStatus = data.child("status").value.toString()
                        sendButton.isEnabled = true
                        userIsReady(currentUserStatus, otherUserId, otherUserStatus)
                    }
                })

        sendButton.setOnClickListener {
            if (intent.extras.get(userName).toString().equals("").not()) {
                val friendlyMessage = FriendlyMessage(
                        currentUser,
                        messageEdit.text.toString().trim(),
                        currentUserName.trim())

                databaseReference.child("Users").child(currentUser).child("messages").child(otherUserId).push().setValue(friendlyMessage)
                databaseReference.child("Users").child(otherUserId).child("messages").child(currentUser).push().setValue(friendlyMessage)

                messageEdit.setText("")
                val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
            }
        }
    }

    private fun userIsReady(currentUserStatus: String, otherUserId: String, otherUserStatus: String) {
        if (chatAdapter == null) {
            chatAdapter = ChatAdapter(currentUser, currentUserStatus, otherUserId, otherUserStatus, this)
            messageRecyclerView.adapter = chatAdapter
            chatAdapter?.startListening()
        }
    }

    override fun onStart() {
        super.onStart()
        chatAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        chatAdapter?.stopListening()
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

    companion object {
        const val userId: String = "id"
        const val userName: String = "name"
        const val userStatus: String = "userStatus"
        const val profileImage: String = "imageLink"
    }
}
