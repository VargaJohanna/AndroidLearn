package com.johanna.chatapp.activities.adapters

import android.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.johanna.chatapp.activities.fragments.ChatsFragment
import com.johanna.chatapp.activities.fragments.UsersFragment

class SectionPageAdapter(fm: android.support.v4.app.FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return UsersFragment()
            1 -> return ChatsFragment()
            else -> {
                return UsersFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "USERS"
            1 -> "CHATS"
            else -> "Select a tab"
        }
    }
}