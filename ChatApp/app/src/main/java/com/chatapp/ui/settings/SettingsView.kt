package com.chatapp.ui.settings

interface SettingsView {
    fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String)
}