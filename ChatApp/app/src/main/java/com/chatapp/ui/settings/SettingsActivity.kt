package com.chatapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.chatapp.R
import com.chatapp.data.settings.SettingsPresenter
import com.chatapp.ui.status.StatusActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsView {
    private val settingsPresenter = SettingsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.apply {
            title = getString(R.string.settings_tab)
            setDisplayHomeAsUpEnabled(true)
        }

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

    override fun updateUserDetails(userDisplayName: String, userStatusData: String, userThumbImage: String) {
        settingsStatus.text = userStatusData
        settingsDisplayName.text = userDisplayName

        Picasso.with(this)
                .load(userThumbImage)
                .placeholder(R.drawable.profile_img)
                .into(settingsProfileImage)
    }
}
