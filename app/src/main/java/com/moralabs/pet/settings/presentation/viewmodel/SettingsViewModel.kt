package com.moralabs.pet.settings.presentation.viewmodel

import com.moralabs.pet.core.domain.AuthenticationUseCase
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.domain.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase,
    private val authenticationUseCase: AuthenticationUseCase
) : BaseViewModel<SettingsDto>(useCase) {

    fun logout(){
        authenticationUseCase.logout()
    }
}