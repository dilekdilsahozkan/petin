package com.moralabs.pet.newPost.domain

import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.data.repository.NewPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewPostUseCase @Inject constructor(
    private val newPostRepository: NewPostRepository
) : BaseUseCase() {
    fun newPost(createNewPost: NewPostDto): Flow<BaseResult<List<PostDto>>> {
        return flow {
            if (createNewPost.text.isNullOrEmpty()) {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.EMPTY_NEW_POST_TEXT)))
            } else {
                val response = newPostRepository.createPost(createNewPost)
                if(response.isSuccessful && response.code() == 200){
                    response.body()?.data?.let {
                        emit(
                            BaseResult.Success(it)
                        )
                    }
                }else {
                    emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
                }
            }
        }
    }
}