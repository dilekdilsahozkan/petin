package com.moralabs.pet.core.presentation.ui

import android.app.NotificationManager
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.observable.NotificationHandler
import com.moralabs.pet.notification.domain.NotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PetFirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        notificationUseCase.sendNotificationToken()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("MAIN", "FIREBASE key -> $message")

        notificationHandler.checkNotifications()

        message.data["message"]?.let{
            sendNotification(it)
        } ?: run {
            sendNotification(message.notification?.body)
        }
    }

    private fun sendNotification(messageBody: String?) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "Default")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(1000, 1000))
                .setSmallIcon(R.drawable.ic_petin_logo)
        notificationBuilder.setContentText(messageBody)
        notificationBuilder.setAutoCancel(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify((Math.random() * 100000).toInt(), notificationBuilder.build())
    }
}