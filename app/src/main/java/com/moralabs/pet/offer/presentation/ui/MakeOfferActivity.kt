package com.moralabs.pet.offer.presentation.ui

import android.os.Bundle
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityOfferBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeOfferActivity : BaseActivity<ActivityOfferBinding>(),
    PetToolbarListener {

    companion object {
        const val POST_ID = "postId"
        const val OFFER_TYPE = "offer_type"
    }

    override fun getLayoutId() = R.layout.activity_offer

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