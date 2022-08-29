package com.moralabs.pet.petProfile.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPetProfileBinding
import com.moralabs.pet.profile.data.remote.dto.ProfileDto
import com.moralabs.pet.profile.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetProfileFragment : BaseFragment<FragmentPetProfileBinding, ProfileDto, ProfileViewModel>() {

    override fun getLayoutId() = R.layout.fragment_pet_profile

    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<ProfileDto> {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}