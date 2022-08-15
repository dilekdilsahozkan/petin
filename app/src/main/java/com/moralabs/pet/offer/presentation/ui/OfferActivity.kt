package com.moralabs.pet.offer.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityOfferBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferActivity : BaseActivity<ActivityOfferBinding>() ,
    PetToolbarListener {

    private lateinit var navController: NavController

    override fun getLayoutId() = R.layout.activity_offer

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_offer) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.appBar)
    }
}