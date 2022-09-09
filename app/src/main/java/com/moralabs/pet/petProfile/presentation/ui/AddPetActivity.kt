package com.moralabs.pet.petProfile.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityAddPetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPetActivity : BaseActivity<ActivityAddPetBinding>() {

    override fun getLayoutId() = R.layout.activity_add_pet

}