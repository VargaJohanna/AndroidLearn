package com.johanna.chatapp.activities.registration

interface RegistrationView {
    fun registrationError(error: String)
    fun updateDatabaseSuccess(displayName: String)
    fun updateDatabaseFail()
}