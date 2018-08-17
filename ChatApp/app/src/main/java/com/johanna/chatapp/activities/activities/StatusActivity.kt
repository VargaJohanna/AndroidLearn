package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    companion object {
        const val status = "default"
    }

    var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title = "Status"

        if (intent.extras != null) {
            changeStatusCard.setText(intent.extras.get(status).toString())
        } else {
            changeStatusCard.setText(getString(R.string.hint_status_enter))
        }
        statusUpdateButton.setOnClickListener {
            val userUid = FirebaseAuth.getInstance().currentUser?.uid

                if (userUid != null) {
                    mDatabase = FirebaseDatabase.getInstance().reference
                            .child("Users")
                            .child(userUid)
                    val currentStatus = changeStatusCard.text.toString().trim()

                    mDatabase!!.child("status")
                            .setValue(currentStatus)
                            .addOnCompleteListener { task: Task<Void> ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, SettingsActivity::class.java))
                                } else {
                                    Toast.makeText(this, "Status not updated", Toast.LENGTH_SHORT).show()
                                }
                            }
                }
            }



    }
}
