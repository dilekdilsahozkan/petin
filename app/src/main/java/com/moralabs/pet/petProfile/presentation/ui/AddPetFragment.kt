package com.moralabs.pet.petProfile.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddPetBinding
import com.moralabs.pet.databinding.ItemAddPetFeatureBinding
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.BR
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPetFragment : BaseFragment<FragmentAddPetBinding, PetDto, PetProfileViewModel>() {

    override fun getLayoutId() = R.layout.fragment_add_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PetDto> {
        val viewModel: PetProfileViewModel by viewModels()
        return viewModel
    }

    private val attributeAdapter: BaseListAdapter<PetAttributeDto, ItemAddPetFeatureBinding> by lazy {
        BaseListAdapter(R.layout.item_add_pet_feature, BR.attribute)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = attributeAdapter

    }

    override fun stateSuccess(data: PetDto) {
        super.stateSuccess(data)

        attributeAdapter.submitList(data.petAttributes)

    }
}