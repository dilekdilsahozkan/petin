package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.moralabs.pet.R
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.CurvedBottomNavigationView
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener{

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun getLayoutId() = R.layout.activity_main_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main_page) as NavHostFragment
        navController = navHostFragment.navController

        binding.dashboardNavigation.setupWithNavController(navController)
        binding.dashboardNavigation.itemIconTintList = null

        bottomNavigationView = findViewById(R.id.dashboard_navigation)

        //initBottomNavigation()

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home,
            R.id.messages,
            R.id.addButton,
            R.id.notification,
            R.id.profile,
        )
            .build()

        setSupportActionBar(binding.appBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initBottomNavigation() = with(bottomNavigationView) {
        setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.addButton) {
                binding.addPostButton.setOnClickListener {
                    print("New Post")
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}