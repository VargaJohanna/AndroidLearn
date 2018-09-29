package com.chatapp.dashboard

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.chatapp.fragments.ChatsFragment
import com.chatapp.fragments.UsersFragment

class SectionPageAdapter(fm: android.support.v4.app.FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UsersFragment()
            1 -> ChatsFragment()
            else -> UsersFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "USERS"
            1 -> "CHATS"
            else -> "Select a tab"
        }
    }
}