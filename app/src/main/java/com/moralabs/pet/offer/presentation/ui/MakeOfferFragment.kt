package com.moralabs.pet.offer.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMakeOfferBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.offer.data.remote.dto.OfferRequestDto
import com.moralabs.pet.offer.presentation.viewmodel.MakeOfferViewModel
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeOfferFragment :
    BaseFragment<FragmentMakeOfferBinding, CreateOfferDto, MakeOfferViewModel>() {

    private val postId: String? by lazy {
        activity?.intent?.getStringExtra(MakeOfferActivity.POST_ID)
    }

    private val offerType: Int? by lazy {
        activity?.intent?.getIntExtra(MakeOfferActivity.OFFER_TYPE, 0)
    }

    override fun getLayoutId() = R.layout.fragment_make_offer
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreateOfferDto> {
        val viewModelMake: MakeOfferViewModel by viewModels()
        return viewModelMake
    }

    private val petAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = { selected ->
            petAdapter.currentList.forEach { pet ->
                pet.selected = pet == selected
            }
            petAdapter.notifyDataSetChanged()
        })
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.offer))
    }

    override fun stateSuccess(data: CreateOfferDto) {
        super.stateSuccess(data)
        petAdapter.submitList(data.getOffer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.petList.adapter = petAdapter

        if (offerType == 3) {
            binding.petList.visibility = View.GONE
            binding.selectText.visibility = View.GONE
            binding.selectPartner.visibility = View.GONE
            binding.selectAdoption.visibility = View.VISIBLE
        } else if (offerType == 2) {
            binding.petList.visibility = View.VISIBLE
            binding.selectText.visibility = View.VISIBLE
            binding.selectPartner.visibility = View.VISIBLE
            binding.selectAdoption.visibility = View.GONE

        }
    }

    override fun addListeners() {
        super.addListeners()
        binding.makeOfferButton.setOnClickListener {
            val pet = petAdapter.currentList.filter { it.selected }.firstOrNull()
            viewModel.newOffer(
                OfferRequestDto(
                    postId = postId,
                    text = binding.selectPartner.text.toString(),
                    petId = pet?.id
                )
            )
            Toast.makeText(context, "Teklifiniz Gönderilidi", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, MainPageActivity::class.java))
        }
        viewModel.petValue()
    }
}

internal enum class OfferType(val type: Int) {
    POST_TYPE(0),
    QAN_TYPE(1),
    FIND_PARTNER_TYPE(2),
    ADOPTION_TYPE(3)
}