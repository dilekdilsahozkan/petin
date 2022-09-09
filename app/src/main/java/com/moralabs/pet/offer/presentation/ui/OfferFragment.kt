package com.moralabs.pet.offer.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentOfferBinding
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.presentation.viewmodel.MakeOfferViewModel
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : BaseFragment<FragmentOfferBinding, OfferDetailDto, OfferViewModel>() {

    private val offerId: String? by lazy {
        activity?.intent?.getStringExtra(OfferActivity.OFFER_ID)
    }
    private val petImage: String? by lazy {
        activity?.intent?.getStringExtra(OfferUserActivity.PET_IMAGE)
    }
    private val petName: String? by lazy {
        activity?.intent?.getStringExtra(OfferUserActivity.PET_NAME)
    }
    private val petAge: String? by lazy {
        activity?.intent?.getStringExtra(OfferUserActivity.PET_AGE)
    }
    private val petGender: String? by lazy {
        activity?.intent?.getStringExtra(OfferUserActivity.PET_GENDER)
    }
    private val petKind: String? by lazy {
        activity?.intent?.getStringExtra(OfferUserActivity.PET_KIND)
    }

    override fun getLayoutId() = R.layout.fragment_offer
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<OfferDetailDto> {
        val viewModelMake: OfferViewModel by viewModels()
        return viewModelMake
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.offer))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOffer(offerId)
        binding.petKind.text = petKind
        binding.petAge.text = petAge
        binding.petGender.text = petGender
        binding.offerImage.loadImage(petImage)
        binding.petName.text = petName
    }
}