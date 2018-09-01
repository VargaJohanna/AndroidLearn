package com.johanna.chatapp.activities.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.johanna.chatapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val GALLERY_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().reference.child("Users")
                    .child(userId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userDisplayName = dataSnapshot.child("display_name").value
                            val userStatusData = dataSnapshot.child("status").value
                            val userThumbImage = "https://api.adorable.io/avatars/145/$userStatusData.png"

                            settingsStatus.text = userStatusData.toString()
                            settingsDisplayName.text = userDisplayName.toString()

                            Picasso.with(this@SettingsActivity)
                                    .load(userThumbImage)
                                    .placeholder(R.drawable.profile_img)
                                    .into(settingsProfileImage)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
