package com.moralabs.pet.petProfile.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddPetBinding
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.data.remote.dto.PetRequestDto
import com.moralabs.pet.petProfile.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPetFragment : BaseFragment<FragmentAddPetBinding, List<PetDto>, PetViewModel>() {

    override fun getLayoutId() = R.layout.fragment_add_pet

    override fun fragmentViewModel(): BaseViewModel<List<PetDto>> {
        val viewModel: PetViewModel by viewModels()
        return viewModel
    }

    override fun stateSuccess(data: List<PetDto>) {
        super.stateSuccess(data)
        viewModel.addPet(
            PetRequestDto(
                media = listOf(MediaDto()),
                petAttributes = listOf(PetAttributeDto())
            )
        )
    }
}