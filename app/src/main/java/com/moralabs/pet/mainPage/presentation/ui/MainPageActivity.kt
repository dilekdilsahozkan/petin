package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.moralabs.pet.R
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.databinding.ActivityMainPageBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetFragment
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.NewPostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>(),
    PetToolbarListener, ChooseTypeBottomSheetListener {

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
                this@MainPageActivity,
                this@MainPageActivity
            ).show(supportFragmentManager, "")
            /*
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.choose_type_bottom_sheet, null)
            dialog.setContentView()
            dialog.show()
            */



        }
    }

    lateinit var btnShowBottomSheet: Button


    override fun onItemClick(type: Int) {
        if (type == 0) {
            startActivity(NewPostActivity.newIntent(this))
        }
        if (type == 1) {
            startActivity(NewPostActivity.newIntent(this))
        }
        if (type == 2) {
            startActivity(NewPostActivity.newIntent(this))
        }
        if (type == 3) {
            startActivity(NewPostActivity.newIntent(this))
        }
    }
}