package com.chatapp.ui.status

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.chatapp.R
import com.chatapp.data.status.StatusPresenter
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity(), StatusView {
    private val statusPresenter = StatusPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        supportActionBar?.title = getString(R.string.status)

        if (intent.extras != null) {
            changeStatusCard.setText(intent.extras.get(status).toString())
        } else {
            changeStatusCard.setText(getString(R.string.hint_status_enter))
        }
        statusUpdateButton.setOnClickListener {
            val currentStatus = changeStatusCard.text.toString().trim()
            statusPresenter.fetchUserDetails(currentStatus)
        }
    }

    override fun statusUpdateSuccess() {
        Toast.makeText(this, getString(R.string.statusUpdated), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun statusUpdateFail() {
        Toast.makeText(this, getString(R.string.statusNotUpdated), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val status = "default"
    }
}
