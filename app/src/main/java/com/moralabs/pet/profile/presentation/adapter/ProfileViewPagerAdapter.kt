package com.moralabs.pet.profile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moralabs.pet.profile.presentation.ui.ProfilePetFragment
import com.moralabs.pet.profile.presentation.ui.ProfilePostFragment

private const val NUM_TABS = 2

class ProfileViewPagerAdapter(fragment: Fragment, private val fragmentItems: List<ProfileViewPagerFragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfilePostFragment()
            else -> ProfilePetFragment()
        }
    }
}
