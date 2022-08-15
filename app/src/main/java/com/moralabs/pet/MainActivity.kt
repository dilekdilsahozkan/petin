package com.moralabs.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moralabs.pet.databinding.ActivityMainBinding
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import com.moralabs.pet.onboarding.presentation.ui.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))

    }
}