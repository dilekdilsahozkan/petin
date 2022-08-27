package com.moralabs.pet.notification.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(),
    PetToolbarListener {

    companion object {
        const val NOTIFICATION_TEXT = "text"
        const val NOTIFICATION_TYPE = "type"
        const val NOTIFICATION_DATETIME = "dateTime"
    }

    override fun getLayoutId() = R.layout.activity_notification
    
}