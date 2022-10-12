package com.moralabs.pet.offer.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentOfferBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.offer.data.remote.dto.OfferDetailDto
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import com.moralabs.pet.petProfile.presentation.ui.PetProfileActivity
import kotlinx.coroutines.flow.collect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferFragment : BaseFragment<FragmentOfferBinding, OfferDetailDto, OfferViewModel>() {

    private val offerId: String? by lazy {
        arguments?.getString(MakeOfferActivity.OFFER_ID)
    }
    private val offerType: Int? by lazy {
        arguments?.getInt(MakeOfferActivity.OFFER_TYPE, 0)
    }
    private val userName: String? by lazy {
        arguments?.getString(OfferUserActivity.USER_NAME)
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
    private val otherUserId: String? by lazy {
        arguments?.getString(OfferUserActivity.OTHER_USER_ID)
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
                PetProfileActivity.PET_ID to petId,
                PetProfileActivity.OTHER_USER_ID to otherUserId,
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
        binding.userInfo.text = userName
        binding.petKind.text = petKind
        binding.petAge.text = petAge
        binding.petGender.text = petGender
        binding.offerImage.loadImage(petImage)
        binding.petName.text = petName

        binding.acceptButton.setOnClickListener {
            viewModel.acceptOffer(offerId)
        }
        binding.declineButton.setOnClickListener {
            viewModel.declineOffer(offerId)
        }
    }
}