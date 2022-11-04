package com.moralabs.pet.settings.presentation.ui.offers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.databinding.FragmentMyOffersBinding
import com.moralabs.pet.databinding.ItemMyOffersBinding
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.presentation.ui.MakeOfferActivity
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOffersFragment : BaseFragment<FragmentMyOffersBinding, OfferDetailDto, OfferViewModel>() {

    override fun getLayoutId() = R.layout.fragment_my_offers
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<OfferDetailDto> {
        val viewModel: OfferViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.myOffers))
    }

    private val offerAdapter: BaseListAdapter<OfferDto, ItemMyOffersBinding> by lazy {
        BaseListAdapter(R.layout.item_my_offers, BR.item, onRowClick = { offer ->
            var bundle = bundleOf(
                OfferUserActivity.MY_OFFER_ID to offer.id,
                MakeOfferActivity.OFFER_TYPE to offer.id,
                OfferUserActivity.OFFER_TEXT to offer.text,
                OfferUserActivity.OFFER_DATE to offer.dateTime,
                OfferUserActivity.OTHER_USER_ID to offer.user?.userId,
                OfferUserActivity.PET_IMAGE to offer.pet?.media?.url,
                OfferUserActivity.PET_ID to offer.pet?.id,
                OfferUserActivity.PET_NAME to offer.pet?.name,
                OfferUserActivity.PET_KIND to offer.pet?.petAttributes?.filter { it.attributeType == 6 }
                    ?.getOrNull(0)?.choice,
                OfferUserActivity.PET_GENDER to offer.pet?.petAttributes?.filter { it.attributeType == 8 }
                    ?.getOrNull(0)?.choice,
                OfferUserActivity.PET_AGE to offer.pet?.petAttributes?.filter { it.attributeType == 5 }
                    ?.getOrNull(0)?.choice
            )
            findNavController().navigate(
                R.id.action_myOffersFragment_to_offerDetailFragment,
                bundle
            )
        }, isSameDto = { oldItem, newItem ->
            oldItem == newItem
        }, emptyString = getString(R.string.noOffer))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.myOffers()
        binding.recyclerview.adapter = offerAdapter
    }

    override fun stateSuccess(data: OfferDetailDto) {
        super.stateSuccess(data)
        offerAdapter.submitList(data.allOffers)
    }
}

enum class StatusType(val type: Int) {
    None(0),
    Accepted(1),
    Declined(2),
    InProgress(3),
}