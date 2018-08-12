package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    val mDatabase = FirebaseDatabase.getInstance().reference
    val mcurrentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userId = mcurrentUser?.uid
        if (userId != null) {
            mDatabase.child("Users")
                    .child(userId)
        }

        mDatabase.addValueEventListener(object: ValueEventListener {
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

        settingsChangeStatus.setOnClickListener{
            val intent = Intent(this, StatusActivity::class.java)
            intent.putExtra(StatusActivity.status, settingsStatus.text.toString().trim())
            startActivity(intent)
        }
    }

}
