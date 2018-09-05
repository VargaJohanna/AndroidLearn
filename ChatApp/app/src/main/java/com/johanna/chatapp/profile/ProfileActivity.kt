package com.johanna.chatapp.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.johanna.chatapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileView {
    private val profilePresenter by lazy {ProfilePresenter(userId, this)}
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.apply {
            title = getString(R.string.profile_tab)
            setDisplayHomeAsUpEnabled(true)
        }

        userId = intent.extras.get(userId).toString()
        profilePresenter.setUpProfile()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun linkProfileDetails(displayName: String, status: String, image: String) {
        profileName.text = displayName
        profileStatus.text = status

        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.profile_img)
                .into(profilePicture)
    }

    companion object {
        const val userId: String = "id"
    }
}
