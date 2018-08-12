package com.johanna.chatapp.activities.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.R
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    companion object {
        const val status = "default"
    }

    val mDatabase = FirebaseDatabase.getInstance().reference
    val mcurrentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title = "Status"

        if(intent.extras != null) {
            changeStatusCard.setText(intent.extras.get(status).toString())
        } else {
            changeStatusCard.setText(getString(R.string.hint_status_enter))
        }

        statusUpdateButton.setOnClickListener {

        }
    }
}
