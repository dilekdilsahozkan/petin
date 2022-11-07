package com.moralabs.pet.offer.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentOfferBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import com.moralabs.pet.petProfile.presentation.ui.PetProfileActivity
import kotlinx.coroutines.flow.collect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferFragment : BaseFragment<FragmentOfferBinding, OfferDetailDto, OfferViewModel>() {

    private val offerId: String? by lazy {
        activity?.intent?.getStringExtra(OfferActivity.OFFER_ID)
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

    override fun addListeners() {
        super.addListeners()

        binding.petInfo.setOnClickListener {
            val bundle = bundleOf(
                PetProfileActivity.PET_ID to viewModel.latestDto?.readOffer?.pet?.id,
                PetProfileActivity.OTHER_USER_ID to viewModel.latestDto?.readOffer?.user?.userId,
            )
            val intent = Intent(context, PetProfileActivity::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.declineState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        startActivity(Intent(context, MainPageActivity::class.java))
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.decline_offer),
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
        lifecycleScope.launch {
            viewModel.acceptState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        startActivity(Intent(context, MainPageActivity::class.java))
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.accept_offer),
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

        binding.petInfo.visibility = View.GONE

        viewModel.getOffer(offerId)
    }

    override fun stateSuccess(data: OfferDetailDto) {
        super.stateSuccess(data)

        binding.offerText.text = data.readOffer?.text
        binding.userInfo.text = data.readOffer?.user?.fullName
        binding.petImage.loadImage(data.readOffer?.pet?.media?.url)
        binding.petName.text = data.readOffer?.pet?.name
        binding.petAge.text = data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 5 }
            ?.getOrNull(0)?.choice
        binding.petType.text = data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 6 }
            ?.getOrNull(0)?.choice
        binding.petGender.text = data.readOffer?.pet?.petAttributes?.filter { it.attributeType == 8 }
            ?.getOrNull(0)?.choice

        if (data.readOffer?.pet == null) {
            binding.petInfo.visibility = View.GONE
        } else {
            binding.petInfo.visibility = View.VISIBLE

            binding.acceptButton.setOnClickListener {
                viewModel.acceptOffer(offerId)
            }
            binding.declineButton.setOnClickListener {
                viewModel.declineOffer(offerId)
            }
        }
    }
}