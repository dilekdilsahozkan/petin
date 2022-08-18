package com.moralabs.pet.core.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moralabs.pet.core.presentation.ui.BaseViewPagerFragment

class BaseViewPagerAdapter(fragment: Fragment, private val fragmentItems: List<BaseViewPagerFragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragmentItems.size

    override fun createFragment(position: Int) = fragmentItems[position]
}