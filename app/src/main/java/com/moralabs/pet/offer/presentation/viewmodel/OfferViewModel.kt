package com.moralabs.pet.offer.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.offer.data.remote.dto.OfferDto
import com.moralabs.pet.offer.domain.OfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val useCase: OfferUseCase
): BaseViewModel<OfferDto>(useCase) {
}