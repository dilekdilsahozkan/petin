package com.moralabs.pet.settings.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivitySettingsBinding
import com.moralabs.pet.profile.presentation.ui.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_settings
    private lateinit var navController: NavController

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showTitleText(getString(R.string.settings_title))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_settings) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.settings_navigation)

        if(intent.extras?.getBoolean(ProfileFragment.NAVIGATE_TO_PROFILE_INFORMATION, false) == true){
            graph.setStartDestination(R.id.personalInformationFragment)
        } else{
            graph.setStartDestination(R.id.settingsFragment)
        }
        navController.setGraph(graph, null)

        setSupportActionBar(binding.appBar)
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }
}