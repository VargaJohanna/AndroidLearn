package com.chatapp.chat

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.chatapp.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), ChatView {
    private val chatPresenter = ChatPresenter(this)
    private val linearLayoutManager = LinearLayoutManager(this)
    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        linearLayoutManager.stackFromEnd = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val otherUserId = intent.extras.getString(userId)
        val otherUserStatus = intent.extras.getString(userStatus)

        messageRecyclerView.layoutManager = linearLayoutManager
        sendButton.isEnabled = false
        chatPresenter.fetchUserDetails(otherUserId, otherUserStatus, this)

        sendButton.setOnClickListener {
            if ((intent.extras.get(userName).toString() == "").not()) {
                chatPresenter.saveMessages(otherUserId)
                messageEdit.setText("")
                hideKeyboard()
            }
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

    override fun enableSendButton() {
        sendButton.isEnabled = true
    }

    override fun setMessageRecyclerView(chatAdapter: ChatAdapter) {
        messageRecyclerView.adapter = chatAdapter
    }

    override fun getMessage(): String {
        return messageEdit.text.toString().trim()
    }

    override fun createChatAdapter(currentUser: String, currentUserStatus: String, otherUserId: String, otherUserStatus: String, context: ChatActivity) {
        var chatAdapter: ChatAdapter? = null
        if (chatAdapter == null) {
            chatAdapter = ChatAdapter(currentUser, currentUserStatus, otherUserId, otherUserStatus, context)
            setMessageRecyclerView(chatAdapter)
            chatAdapter.startListening()
        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

    companion object {
        const val userId: String = "id"
        const val userName: String = "name"
        const val userStatus: String = "userStatus"
        const val profileImage: String = "imageLink"
    }
}
