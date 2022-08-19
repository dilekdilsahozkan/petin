package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.ActivityMainPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() ,
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_main_page
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_newPost) as NavHostFragment
        navController = navHostFragment.navController
        binding.dashboardNavigation.setupWithNavController(navController)
        binding.dashboardNavigation.itemIconTintList = null

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home,
            R.id.messages,
            R.id.addButton,
            R.id.notification,
            R.id.profile,
        )
            .build()

   //    setupActionBarWithNavController(navController, appBarConfiguration)
   }
}