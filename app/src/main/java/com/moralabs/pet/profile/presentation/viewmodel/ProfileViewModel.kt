package com.moralabs.pet.profile.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.profile.data.remote.dto.ProfileDto
import com.moralabs.pet.profile.domain.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
): BaseViewModel<ProfileDto>(useCase) {

}