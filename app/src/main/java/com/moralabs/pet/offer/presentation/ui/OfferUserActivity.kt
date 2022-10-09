package com.moralabs.pet.offer.presentation.ui

import android.os.Bundle
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityOfferUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferUserActivity : BaseActivity<ActivityOfferUserBinding>(),
    PetToolbarListener {

    companion object {
        const val POST_ID = "postId"
        const val OFFER_TEXT = "offerText"
        const val USER_NAME = "userName"
        const val OTHER_USER_ID = "otherUserId"
        const val PET_ID = "petId"
        const val PET_IMAGE = "petImage"
        const val PET_NAME = "petName"
        const val PET_AGE = "petAge"
        const val PET_GENDER = "petGender"
        const val PET_KIND = "petKind"
    }

    override fun getLayoutId() = R.layout.activity_offer_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBar)
    }

    override fun showTitleText(title: String?) {
        binding.appBar.showTitleText(title)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }
}