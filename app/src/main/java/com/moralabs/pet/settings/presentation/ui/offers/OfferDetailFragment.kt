package com.moralabs.pet.settings.presentation.ui.offers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentOfferDetailBinding
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferDetailFragment :
    BaseFragment<FragmentOfferDetailBinding, OfferDetailDto, OfferViewModel>() {

    private val offerId: String? by lazy {
        arguments?.getString(OfferActivity.OFFER_ID)
    }

    override fun getLayoutId() = R.layout.fragment_offer_detail
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<OfferDetailDto> {
        val viewModelMake: OfferViewModel by viewModels()
        return viewModelMake
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.myOffer))
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        activity?.onBackPressed()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.delete_offer),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOffer(offerId)
    }

    override fun stateSuccess(data: OfferDetailDto) {
        super.stateSuccess(data)

        binding.offerText.text = data.readOffer?.text
        binding.petImage.loadImage(data.readOffer?.pet?.media?.url)
        binding.petName.text = data.readOffer?.pet?.name
        binding.petAge.text = data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 5 }
            ?.getOrNull(0)?.choice
        binding.petType.text = data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 6 }
            ?.getOrNull(0)?.choice
        binding.petGender.text =
            data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 8 }
                ?.getOrNull(0)?.choice

        if (data.readOffer?.pet == null) {
            binding.petInfo.visibility = View.GONE
        } else {
            binding.petInfo.visibility = View.VISIBLE
        }

        binding.deleteButton.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okay = getString(R.string.yes),
                discard = getString(R.string.no),
                description = resources.getString(R.string.deleteOfferSure),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        viewModel.deleteOffer(offerId)
                    }
                }
            ).show()
        }
    }
}
