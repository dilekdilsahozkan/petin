package com.moralabs.pet.profile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moralabs.pet.profile.presentation.ui.ProfilePetsFragment
import com.moralabs.pet.profile.presentation.ui.ProfilePostsFragment

private const val NUM_TABS = 2

class ProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfilePostsFragment()
            else -> ProfilePetsFragment()
        }
    }
}