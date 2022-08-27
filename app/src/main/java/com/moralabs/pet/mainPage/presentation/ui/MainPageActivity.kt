package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.NewPostActivity
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener, ChooseTypeBottomSheetListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView
    var selectedType: Int = 0

    override fun getLayoutId() = R.layout.activity_main_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_page) as NavHostFragment
        navController = navHostFragment.navController

        binding.dashboardNavigation.setupWithNavController(navController)
        binding.dashboardNavigation.itemIconTintList = null
        bottomNavigationView = findViewById(R.id.dashboard_navigation)

        onClick()

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

    private fun onClick() {
        binding.addPostButton.setOnClickListener {
            ChooseTypeBottomSheetFragment(
                this@MainPageActivity
            ).show(supportFragmentManager, "")
        }
    }

    override fun onItemClick(type: Int) {

        if (type == 0) {
            selectedType = TabTextType.POST_TYPE.type
        }
        if (type == 1) {
            selectedType = TabTextType.QAN_TYPE.type
        }
        if (type == 2) {
            selectedType = TabTextType.FIND_PARTNER_TYPE.type
        }
        if (type == 3) {
            selectedType = TabTextType.ADOPTION_TYPE.type
        }

        startActivity(Intent(this@MainPageActivity, NewPostActivity::class.java).apply {
            this.putExtras(
                bundleOf(NewPostActivity.BUNDLE_CHOOSE_TYPE to selectedType)
            )
        })

    }
}