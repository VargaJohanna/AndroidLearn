package com.johanna.chatapp.activities.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase

import com.johanna.chatapp.R
import com.johanna.chatapp.activities.settings.UsersAdapter
import kotlinx.android.synthetic.main.fragment_users.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UsersFragment : Fragment() {
    val mDatabase = FirebaseDatabase.getInstance().reference.child("Users").limitToFirst(50)
    val usersAdapter: UsersAdapter by lazy { UsersAdapter(mDatabase, requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val mDatabase = FirebaseDatabase.getInstance().reference.child("Users").limitToFirst(50)
        userRecyclerView.setHasFixedSize(true)
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
