package com.moralabs.pet.notification.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(),
PetToolbarListener{

    private lateinit var navController: NavController
    override fun getLayoutId() = R.layout.activity_notification

    companion object {
        const val NOTIFICATION_TEXT = "text"
        const val NOTIFICATION_TYPE = "type"
        const val NOTIFICATION_DATETIME = "dateTime"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_notification) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.appBar)
    }
}