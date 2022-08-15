package com.moralabs.pet.mainPage.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.mainPage.data.remote.dto.MainPageDto
import com.moralabs.pet.mainPage.domain.MainPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCase: MainPageUseCase
): BaseViewModel<MainPageDto>(useCase){

}