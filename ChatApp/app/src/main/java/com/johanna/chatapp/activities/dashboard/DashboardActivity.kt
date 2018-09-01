package com.johanna.chatapp.activities.dashboard

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.main.MainActivity
import com.johanna.chatapp.activities.activities.SettingsActivity
import com.johanna.chatapp.activities.adapters.SectionPageAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    companion object {
        const val name: String = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) supportActionBar.title = "Dashboard"

        val sectionAdapter = SectionPageAdapter(supportFragmentManager)
        dashboardViewPager.adapter = sectionAdapter
        tabView.setupWithViewPager(dashboardViewPager)
        tabView.setTabTextColors(Color.WHITE, R.color.abc_primary_text_material_dark)

        if (intent.extras != null) {
            val userName = intent.extras.get(name)
            Toast.makeText(this, userName.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item != null) {
            if(item.itemId.equals(R.id.logoutMenu)) {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            if(item.itemId.equals(R.id.settingsMenu)) {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return true
    }
}
