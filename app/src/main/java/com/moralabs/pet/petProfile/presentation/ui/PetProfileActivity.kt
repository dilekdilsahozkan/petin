package com.moralabs.pet.petProfile.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityPetProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetProfileActivity : BaseActivity<ActivityPetProfileBinding>() {

    companion object {
        const val PET_ID = "petId"
        const val OTHER_USER_ID = "otherUserId"
    }

    override fun getLayoutId() = R.layout.activity_pet_profile

}