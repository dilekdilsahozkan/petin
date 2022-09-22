package com.moralabs.pet.onboarding.presentation.ui.guest

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityGuestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestActivity : BaseActivity<ActivityGuestBinding>(),
    PetToolbarListener {

    override fun getLayoutId() = R.layout.activity_guest

}