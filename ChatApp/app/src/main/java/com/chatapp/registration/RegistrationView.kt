package com.chatapp.registration

interface RegistrationView {
    fun registrationError(error: String)
    fun updateDatabaseSuccess(displayName: String)
    fun updateDatabaseFail()
}