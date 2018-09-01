package com.johanna.chatapp.activities.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.johanna.chatapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileView {
    companion object {
        const val userId: String = "id"
    }
    private val profilePresenter = ProfilePresenter(this)
    lateinit var userIdValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userIdValue = intent.extras.get(userId).toString()
        profilePresenter.setUpProfile()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun linkProfileDetails(displayName: String, status: String, image: String) {
        profileName.text = displayName
        profileStatus.text = status

        Picasso.with(this@ProfileActivity)
                .load(image)
                .placeholder(R.drawable.profile_img)
                .into(profilePicture)
    }
}
