package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNewPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostActivity : BaseActivity<ActivityNewPostBinding>(),
PetToolbarListener{

    override fun getLayoutId() = R.layout.activity_new_post

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_message) as NavHostFragment
        navController = navHostFragment.navController
    }

}