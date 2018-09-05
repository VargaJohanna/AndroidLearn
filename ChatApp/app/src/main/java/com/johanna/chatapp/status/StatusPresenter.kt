package com.johanna.chatapp.status

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.Database

class StatusPresenter constructor(private val statusActivity: StatusActivity) {
    fun fetchUserDetails(currentStatus: String) {
        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        if (userUid != null) {
            val userReference = FirebaseDatabase.getInstance().reference
                    .child(Database.usersNode)
                    .child(userUid)

            userReference.child(Database.statusNode)
                    .setValue(currentStatus)
                    .addOnCompleteListener { task: Task<Void> ->
                        statusUpdateResult(task.isSuccessful)
                    }
        }
    }

    private fun statusUpdateResult(success: Boolean) {
        if (success) statusActivity.statusUpdateSuccess() else statusActivity.statusUpdateFail()
    }
}