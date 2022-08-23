package com.moralabs.pet.message.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_profile
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_profile) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.appBar)
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }
}