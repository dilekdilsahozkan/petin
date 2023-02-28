package com.moralabs.pet

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityMainBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.notification.domain.NotificationUseCase
import com.moralabs.pet.onboarding.presentation.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private var connectStatus = ConnectivityStatus.Unset

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        BaseActivity.currentActivity = this
        instance = this
        super.onCreate(savedInstanceState)

        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        lifecycleScope.launch {
            delay(500)
            if (authenticationRepository.isLoggedIn()) {
                startActivity(Intent(applicationContext, MainPageActivity::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, WelcomeActivity::class.java))
                finish()
            }
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        CoroutineScope(Dispatchers.Default).launch {
            notificationUseCase.sendNotificationToken()
        }

        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                connectStatus = ConnectivityStatus.Available
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                if (connectStatus == ConnectivityStatus.Available) {
                    Toast.makeText(applicationContext, R.string.connection_lost, Toast.LENGTH_SHORT).show()
                }

                connectStatus = ConnectivityStatus.Losing
            }

            override fun onLost(network: Network) {
                super.onLost(network)

                if (connectStatus == ConnectivityStatus.Available) {
                    Toast.makeText(applicationContext, R.string.connection_lost, Toast.LENGTH_SHORT).show()
                }

                connectStatus = ConnectivityStatus.Lost
            }

            override fun onUnavailable() {
                super.onUnavailable()

                if (connectStatus == ConnectivityStatus.Available) {
                    Toast.makeText(applicationContext, R.string.connection_lost, Toast.LENGTH_SHORT).show()
                }

                connectStatus = ConnectivityStatus.Unavailable
            }
        })
    }
}

enum class ConnectivityStatus {
    Unset, Available, Unavailable, Losing, Lost
}