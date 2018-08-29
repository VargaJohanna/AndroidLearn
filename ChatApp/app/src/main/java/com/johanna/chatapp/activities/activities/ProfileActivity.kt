package com.johanna.chatapp.activities.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.johanna.chatapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val userId: String = "id"
    }
    val mCurrentUser: FirebaseUser? by lazy {FirebaseAuth.getInstance().currentUser}
    private val mUserDatabase: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userIdValue)}
    lateinit var userIdValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(intent.extras != null) {
            userIdValue = intent.extras.get(userId).toString()
        }
        Log.d("USERID", userId)
        setUpProfile(userIdValue)
    }

    private fun setUpProfile(userId: String) {
        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val displayName = dataSnapshot.child("display_name").value.toString()
                val status = dataSnapshot.child("status").value.toString()
                val image = "https://api.adorable.io/avatars/260/$status.png"

                profileName.text = displayName
                profileStatus.text = status

                Picasso.with(this@ProfileActivity)
                        .load(image)
                        .placeholder(R.drawable.profile_img)
                        .into(profilePicture)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
