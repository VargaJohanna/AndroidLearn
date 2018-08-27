package com.johanna.chatapp.activities.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.johanna.chatapp.R
import com.johanna.chatapp.activities.models.User
import de.hdodenhof.circleimageview.CircleImageView
import android.view.LayoutInflater
import com.google.firebase.database.Query
import com.johanna.chatapp.activities.activities.ChatActivity
import com.johanna.chatapp.activities.activities.ProfileActivity


class UsersAdapter (
        databaseQuery: Query,
        var context: Context
) : FirebaseRecyclerAdapter<User, UsersAdapter.ViewHolder>(
        FirebaseRecyclerOptions.Builder<User>()
                .setQuery(databaseQuery, User::class.java)
                .build()
) {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        lateinit var userNameText: String
        lateinit var userStatusText: String
        lateinit var userProfileImageLink: String

        fun bindView(user: User){
            val userName = itemView.findViewById<TextView>(R.id.userName)
            val userStatus = itemView.findViewById<TextView>(R.id.userStatus)
            val userProfileImage = itemView.findViewById<CircleImageView>(R.id.userProfile)

            //Set the strings so we can pass in the intent
            userNameText = user.display_name.toString()
            userStatusText = user.status.toString()
            userProfileImageLink = user.thumb_image.toString()

            userName.text = userNameText
            userStatus.text = userStatusText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.users_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int, model: User) {
        val userId = getRef(position).key
        holder.bindView(model)

        holder.itemView.setOnClickListener{
            val options = arrayOf("Open User Profile", "Send Message")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose what to do!")
//            builder.setItems(options, DialogInterface.OnClickListener()) {
//                override fun onClick
//            }
            builder.setItems(options, DialogInterface.OnClickListener {
                dialogInterface, i ->
                val userName = holder.userNameText
                val userStatus = holder.userStatusText
                val profileImage = holder.userProfileImageLink
                Log.d("USERID I VALUE", i.toString())

                if(i == 0) {
                    val profileIntent = Intent(context, ProfileActivity::class.java)
                    Log.d("USERID IN ADAPTER", userId)
                    profileIntent.putExtra(ProfileActivity.userId, userId)
                    context.startActivity(profileIntent)
                } else {
                    val chatIntent = Intent(context, ChatActivity::class.java)
                    chatIntent.putExtra(ChatActivity.userId, userId)
                    chatIntent.putExtra(ChatActivity.userName, userName)
                    chatIntent.putExtra(ChatActivity.userStatus, userStatus)
                    chatIntent.putExtra(ChatActivity.profileImage, profileImage)
                    context.startActivity(chatIntent)
                }
            })
            builder.show()
        }
    }
}