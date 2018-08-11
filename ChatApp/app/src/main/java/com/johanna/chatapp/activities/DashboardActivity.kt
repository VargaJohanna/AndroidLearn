package com.johanna.chatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.johanna.chatapp.R

class DashboardActivity : AppCompatActivity() {
    companion object {
        const val name: String = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (intent.extras != null) {
            val userName = intent.extras.get(name)
            Toast.makeText(this, userName.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
