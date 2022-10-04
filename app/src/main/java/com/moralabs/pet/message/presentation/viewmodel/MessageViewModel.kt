package com.moralabs.pet.message.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.domain.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel  @Inject constructor(
    private val useCase: MessageUseCase
): BaseViewModel<List<ChatDto>>(useCase){
    var blocked: MutableLiveData<ChatDto?> = MutableLiveData(null)
}