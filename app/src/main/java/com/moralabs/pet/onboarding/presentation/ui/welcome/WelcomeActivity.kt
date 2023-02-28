package com.moralabs.pet.onboarding.presentation.ui.welcome

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_welcome

    override fun onBackPressed() {
    }
}