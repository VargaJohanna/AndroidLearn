package com.johanna.chatapp.activities.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.status.StatusActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsView {
    companion object {
        const val GALLERY_ID = 1
    }

    val settingsPresenter = SettingsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        settingsPresenter.fetchUSerDetails()

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

    override fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String) {
        settingsStatus.text = userStatusData
        settingsDisplayName.text = userDisplayName

        Picasso.with(this@SettingsActivity)
                .load(userThumbImage)
                .placeholder(R.drawable.profile_img)
                .into(settingsProfileImage)
    }
}
