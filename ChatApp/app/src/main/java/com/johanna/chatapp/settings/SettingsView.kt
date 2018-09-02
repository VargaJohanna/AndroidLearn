package com.johanna.chatapp.settings

interface SettingsView {
    fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String)
}