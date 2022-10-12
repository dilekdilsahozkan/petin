package com.moralabs.pet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.databinding.ActivityMainBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.notification.domain.NotificationUseCase
import com.moralabs.pet.onboarding.presentation.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    companion object {
        var instance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        instance = this
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        CoroutineScope(Dispatchers.Unconfined).launch {
            notificationUseCase.sendNotificationToken().collect {}
        }
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        if (authenticationRepository.isLoggedIn()) {
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}