package com.chatapp.chat

import com.chatapp.ui.chat.ChatActivity

interface ChatView {
    fun enableSendButton()
    fun setMessageRecyclerView(chatAdapter: ChatAdapter)
    fun getMessage(): String
    fun createChatAdapter(currentUser: String, currentUserStatus: String, otherUserId: String, otherUserStatus: String, context: ChatActivity)
}