package com.moralabs.pet.offer.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMakeOfferBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.presentation.viewmodel.OfferViewModel
import com.moralabs.pet.petProfile.data.remote.dto.CreateOfferDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeOfferFragment : BaseFragment<FragmentMakeOfferBinding, CreateOfferDto, OfferViewModel>() {

    override fun getLayoutId() = R.layout.fragment_make_offer
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreateOfferDto> {
        val viewModel: OfferViewModel by viewModels()
        return viewModel
    }

    private val petAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.pet, onRowClick = {selected ->
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
            viewModel.newOffer(
                OfferDto(
                    text = binding.selectPartner.text.toString()
                )
            )
            startActivity(Intent(context, MainPageActivity::class.java))
        }
        viewModel.petValue()
    }
}