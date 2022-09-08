package com.moralabs.pet.petProfile.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPetProfileBinding
import com.moralabs.pet.databinding.ItemPetFeatureBinding
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetProfileViewModel
import com.moralabs.pet.petProfile.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetProfileFragment : BaseFragment<FragmentPetProfileBinding, PetDto, PetProfileViewModel>() {

    private val petId: String? by lazy {
        activity?.intent?.getStringExtra(PetProfileActivity.PET_ID)
    }

    override fun getLayoutId() = R.layout.fragment_pet_profile
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PetDto> {
        val viewModel: PetProfileViewModel by viewModels()
        return viewModel
    }

    private val attributeAdapter: BaseListAdapter<PetAttributeDto, ItemPetFeatureBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_feature, BR.item, onRowClick = {

        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.attributeRecycler.adapter = attributeAdapter
        addObservers()
    }

    override fun addListeners() {
        super.addListeners()

        viewModel.petInfo(petId)
    }

    override fun stateSuccess(data: PetDto) {
        super.stateSuccess(data)

        attributeAdapter.submitList(data.petAttributes)

        binding.petName.text = data.name.toString()
        binding.petImage.loadImage(data.media?.url)

        data.petAttributes?.forEach {
                when(it.type){

                    8 -> binding.petGender.text = it.choice.toString()

                    4 -> binding.locationName.text = it.choice.toString()

                    7 -> binding.petType.text = it.choice.toString()
                }
        }
    }
}