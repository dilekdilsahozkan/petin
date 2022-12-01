package com.moralabs.pet.newPost.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.domain.NewPostUseCase
import com.moralabs.pet.petProfile.data.remote.dto.CreatePostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val useCase: NewPostUseCase
) : BaseViewModel<CreatePostDto>(useCase) {

    var files: MutableLiveData<MutableList<File>> = MutableLiveData(mutableListOf())
    var locationId: String? = null
    var selectedPet: MutableLiveData<List<PetDto>?> = MutableLiveData()
    var petList = mutableListOf<PetDto>()

    private var _userInfo: MutableStateFlow<ViewState<UserDto>> = MutableStateFlow(ViewState.Idle())
    val getUser: StateFlow<ViewState<UserDto>> = _userInfo

    private var _createPost: MutableStateFlow<ViewState<CreatePostDto>> = MutableStateFlow(ViewState.Idle())
    val createPost: StateFlow<ViewState<CreatePostDto>> = _createPost

    fun createPost(createNewPost: NewPostDto) {
        viewModelScope.launch {
            useCase.newPost(createNewPost)
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _createPost.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun petValue() {
        viewModelScope.launch {
            useCase.petValue()
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _state.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun userInfo() {
        viewModelScope.launch {
            useCase.userInfo()
                .onStart {
                    _state.value = ViewState.Loading()
                }
                .catch { exception ->
                    _state.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success -> {
                            _state.value = ViewState.Idle()
                            _userInfo.value = ViewState.Success(baseResult.data)
                        }
                        is BaseResult.Error -> {
                            _state.value = ViewState.Error(baseResult.error.code, baseResult.error.message)
                        }
                    }
                }
        }
    }

    fun addFile(file: File){
        var list = files.value
        list?.add(file)
        files.postValue(list)
    }

    fun deleteFile(file: File){
        var list = files.value
        list?.remove(file)
        files.postValue(list)
    }
}

