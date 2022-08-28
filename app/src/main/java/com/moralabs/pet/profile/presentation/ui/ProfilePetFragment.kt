package com.moralabs.pet.profile.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentProfilePetsBinding
import com.moralabs.pet.profile.data.remote.dto.PetDto
import com.moralabs.pet.profile.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilePetFragment : BaseFragment<FragmentProfilePetsBinding, List<PetDto>, PetViewModel>() {

    override fun getLayoutId() = R.layout.fragment_profile_pets

    override fun fragmentViewModel(): BaseViewModel<List<PetDto>> {
        val viewModel: PetViewModel by viewModels()
        return viewModel
    }
}