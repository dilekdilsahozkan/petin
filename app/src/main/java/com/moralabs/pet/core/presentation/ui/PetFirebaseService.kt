package com.moralabs.pet.core.presentation.ui

import com.google.firebase.messaging.FirebaseMessagingService
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PetFirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        notificationUseCase.sendNotificationToken()
    }
}