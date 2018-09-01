package com.johanna.chatapp.activities.status

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class StatusPresenter constructor(private val statusActivity: StatusActivity) {
    fun fetchUserDetails(currentStatus: String) {
        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        if (userUid != null) {
            val userReference = FirebaseDatabase.getInstance().reference
                    .child("Users")
                    .child(userUid)

            userReference!!.child("status")
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