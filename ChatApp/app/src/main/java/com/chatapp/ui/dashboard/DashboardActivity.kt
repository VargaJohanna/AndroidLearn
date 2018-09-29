package com.chatapp.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.chatapp.R
import com.chatapp.dashboard.SectionPageAdapter
import com.chatapp.main.MainActivity
import com.chatapp.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) supportActionBar.title = this.getString(R.string.dashboard)

        val sectionAdapter = SectionPageAdapter(supportFragmentManager)
        dashboardViewPager.adapter = sectionAdapter
        tabView.setupWithViewPager(dashboardViewPager)
        tabView.setTabTextColors(Color.WHITE, R.color.abc_primary_text_material_dark)

        intent.extras?.let{
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
        if (item != null) {
            if (item.itemId == (R.id.logoutMenu)) {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            if (item.itemId == (R.id.settingsMenu)) {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return true
    }

    companion object {
        const val name: String = "name"
    }
}
