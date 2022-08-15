package com.moralabs.pet.newPost.presentation.viewmodel

import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.domain.NewPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val useCase: NewPostUseCase
): BaseViewModel<NewPostDto>(useCase){

}