package com.moralabs.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moralabs.pet.databinding.ActivityMainBinding
import com.moralabs.pet.onboarding.presentation.ui.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        var instance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))

    }
}