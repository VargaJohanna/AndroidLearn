package com.johanna.chatapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.johanna.chatapp.Database

import com.johanna.chatapp.R
import com.johanna.chatapp.settings.UsersAdapter
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() {
    private val database = FirebaseDatabase.getInstance().reference.child(Database.usersNode).limitToFirst(50)
    private val usersAdapter: UsersAdapter by lazy { UsersAdapter(database, requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        userRecyclerView.layoutManager = linearLayoutManager
        userRecyclerView.adapter = usersAdapter
    }

    override fun onStart() {
        super.onStart()
        usersAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        usersAdapter.stopListening()
    }

}
