package com.chatapp.ui.chat

import com.chatapp.chat.ChatAdapter

interface ChatView {
    fun enableSendButton()
    fun setMessageRecyclerView(chatAdapter: ChatAdapter)
    fun getMessage(): String
    fun createChatAdapter(currentUser: String, currentUserStatus: String, otherUserId: String, otherUserStatus: String, context: ChatActivity)
}