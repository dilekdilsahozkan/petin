package com.moralabs.pet.offer.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
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
class MakeOfferFragment : BaseFragment<FragmentMakeOfferBinding, CreateOfferDto, MakeOfferViewModel>() {

    private val postType: String? by lazy {
        activity?.intent?.getStringExtra(OfferActivity.POST_ID)
    }

    override fun getLayoutId() = R.layout.fragment_make_offer
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreateOfferDto> {
        val viewModelMake: MakeOfferViewModel by viewModels()
        return viewModelMake
    }

    private val petAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = {selected ->
            petAdapter.currentList.forEach { pet ->
                pet.selected = pet == selected
            }
            petAdapter.notifyDataSetChanged()
        }, isSameDto = { oldItem, newItem ->
            true
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
    }

    override fun addListeners() {
        super.addListeners()
        binding.makeOfferButton.setOnClickListener {
            val pet = petAdapter.currentList?.filter { it.selected }?.firstOrNull()
            viewModel.newOffer(
                OfferRequestDto(
                    postId = postType,
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