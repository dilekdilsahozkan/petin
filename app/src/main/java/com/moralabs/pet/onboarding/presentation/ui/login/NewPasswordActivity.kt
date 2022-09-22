package com.moralabs.pet.onboarding.presentation.ui.login

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNewPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordActivity : BaseActivity<ActivityNewPasswordBinding>() {

    companion object {
        const val EMAIL = "email"
        const val PASSWORD_CODE = "passwordCode"
    }

    override fun getLayoutId() = R.layout.activity_new_password
}