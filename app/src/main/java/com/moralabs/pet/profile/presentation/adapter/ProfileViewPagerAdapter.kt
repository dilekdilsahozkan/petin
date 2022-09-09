package com.moralabs.pet.profile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileViewPagerAdapter(fragment: Fragment, private val fragmentItems: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragmentItems.size

    override fun createFragment(position: Int) = fragmentItems[position]
}
