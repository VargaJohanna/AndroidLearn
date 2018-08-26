package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val GALLERY_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().reference.child("Users")
                    .child(userId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userDisplayName = dataSnapshot.child("display_name").value
                            val userImage = dataSnapshot.child("image").value
                            val userStatus = dataSnapshot.child("status").value
                            val userThumbImage = dataSnapshot.child("thumb_image").value

                            settingsStatus.text = userStatus.toString()
                            settingsDisplayName.text = userDisplayName.toString()
                        }

                        override fun onCancelled(dataErrorSnapshot: DatabaseError) {
                        }
                    })
        }

        settingsChangeStatus.setOnClickListener {
            val intent = Intent(this, StatusActivity::class.java)
            intent.putExtra(StatusActivity.status, settingsStatus.text.toString().trim())
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
