package com.moralabs.pet.message.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.message.data.remote.dto.MessageDto
import com.moralabs.pet.message.domain.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel  @Inject constructor(
    private val useCase: MessageUseCase
): BaseViewModel<MessageDto>(useCase){

}