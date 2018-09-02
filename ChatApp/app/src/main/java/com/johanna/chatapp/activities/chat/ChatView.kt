package com.johanna.chatapp.activities.chat

interface ChatView {
    fun enableSendButton()
    fun setMessgaRecyclerView(chatAdapter: ChatAdapter)
    fun getMessage(): String
}