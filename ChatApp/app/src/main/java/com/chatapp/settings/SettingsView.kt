package com.chatapp.settings

interface SettingsView {
    fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String)
}