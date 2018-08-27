package com.johanna.chatapp.activities.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.models.Users
import de.hdodenhof.circleimageview.CircleImageView
import android.view.LayoutInflater
import com.google.firebase.database.Query


class UsersAdapter (
        databaseQuery: Query,
        var context: Context
) : FirebaseRecyclerAdapter<Users, UsersAdapter.ViewHolder>(
        FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(databaseQuery, Users::class.java)
                .build()
) {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindView(users: Users){
            val userName = itemView.findViewById<TextView>(R.id.userName)
            val userStatus = itemView.findViewById<TextView>(R.id.userStatus)
            val userProfileImage = itemView.findViewById<CircleImageView>(R.id.userProfile)

            //Set the strings so we can pass in the intent
            val userNameText = users.display_name
            val userStatusText = users.status
            val userProfileImageLink = users.thumb_image

            userName.text = userNameText
            userStatus.text = userStatusText
            Log.d("USERSTATUS", userName.text.toString())
            Log.d("USERSTATUS", userStatus.text.toString())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.users_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int, model: Users) {
        val userId = getRef(position).key
        holder.bindView(model)

        holder.itemView.setOnClickListener{
            //TODO: Create popup dialog where you can choose to message or view profile
            Toast.makeText(context, "User row clicked $userId", Toast.LENGTH_SHORT).show()
        }
    }
}