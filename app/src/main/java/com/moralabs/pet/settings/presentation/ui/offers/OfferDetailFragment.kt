package com.moralabs.pet.settings.presentation.ui.offers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentOfferDetailBinding
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferDetailFragment : BaseFragment<FragmentOfferDetailBinding, OfferDetailDto, OfferViewModel>() {

    private val offerId: String? by lazy {
        arguments?.getString(OfferUserActivity.MY_OFFER_ID)
    }
    private val offerDate: Long? by lazy {
        arguments?.getLong(OfferUserActivity.OFFER_DATE)
    }
    private val offerText: String? by lazy {
        arguments?.getString(OfferUserActivity.OFFER_TEXT)
    }
    private val petImage: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_IMAGE)
    }
    private val petName: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_NAME)
    }
    private val petAge: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_AGE)
    }
    private val petGender: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_GENDER)
    }
    private val petKind: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_KIND)
    }
    private val petId: String? by lazy {
        arguments?.getString(OfferUserActivity.PET_ID)
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

        if(petId == null){
            binding.petInfo.visibility = View.GONE
        }else{
            binding.petInfo.visibility = View.VISIBLE
        }

        binding.offerText.text = offerText
        binding.petKind.text = petKind
        binding.petAge.text = petAge
        binding.petGender.text = petGender
        binding.offerImage.loadImage(petImage)
        binding.petName.text = petName

        binding.deleteButton.setOnClickListener {
            viewModel.deleteOffer(offerId)
        }
    }
}