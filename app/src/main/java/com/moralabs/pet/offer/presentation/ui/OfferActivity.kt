package com.moralabs.pet.offer.presentation.ui

import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityOffer2Binding
import com.moralabs.pet.message.presentation.ui.MessageDetailActivity
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferActivity : BaseActivity<ActivityOffer2Binding>() {

    companion object {
        const val OFFER_ID = "offerId"
    }

    override fun getLayoutId() = R.layout.activity_offer2

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.img_back -> super.onBackPressed()
        }
    }
}