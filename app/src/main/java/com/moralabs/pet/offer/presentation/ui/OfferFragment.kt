package com.moralabs.pet.offer.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentOfferBinding
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : BaseFragment<FragmentOfferBinding, CreateOfferDto, OfferViewModel>() {

    override fun getLayoutId() = R.layout.fragment_offer
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreateOfferDto> {
        val viewModel: OfferViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.offer))
    }
}