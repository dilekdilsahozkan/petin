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
    var allData: List<PetDto>? = null
    var selectedPet: MutableLiveData<List<PetDto>?> = MutableLiveData()

    protected var _userInfo: MutableStateFlow<ViewState<UserDto>> = MutableStateFlow(ViewState.Idle())
    val getUser: StateFlow<ViewState<UserDto>> = _userInfo

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
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _state.value = ViewState.Success(baseResult.data)
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
                    if (baseResult is BaseResult.Success) {
                        _userInfo.value = ViewState.Success(baseResult.data)
                        _state.value = ViewState.Idle()
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

