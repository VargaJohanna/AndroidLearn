package com.johanna.chatapp.activities.settings

interface SettingsView {
    fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String)
}