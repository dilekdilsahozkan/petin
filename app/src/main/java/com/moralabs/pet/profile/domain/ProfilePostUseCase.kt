package com.moralabs.pet.profile.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfilePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) : BaseUseCase() {

    fun profilePost(): Flow<BaseResult<List<PostDto>>> {
        return flow {

            val response = postRepository.profilePost()
            if (response.isSuccessful && response.code() == 200) {
                emit(
                    BaseResult.Success(response.body()?.data ?: listOf())
                )
            } else {
                val error = Gson().fromJson(response.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun getPostAnotherUser(userId: String?): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    postRepository.getPostAnotherUser(userId).body()?.data ?: listOf()
                )
            )
        }
    }

    fun likePost(postId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val like = postRepository.likePost(postId)
            if (like.isSuccessful && like.code() == 200) {
                emit(
                    BaseResult.Success(true)
                )
            } else {
                val error = Gson().fromJson(like.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun unlikePost(postId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val like = postRepository.unlikePost(postId)
            if (like.isSuccessful && like.code() == 200) {
                emit(
                    BaseResult.Success(true)
                )
            } else {
                val error = Gson().fromJson(like.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }

    fun deletePost(postId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val delete = postRepository.deletePost(postId)
            if (delete.isSuccessful && delete.code() == 200) {
                emit(
                    BaseResult.Success(true)
                )
            } else {
                val error = Gson().fromJson(delete.errorBody()?.string(), BaseResponse::class.java)
                emit(
                    BaseResult.Error(
                        ErrorResult(
                            code = ErrorCode.SERVER_ERROR,
                            error.userMessage
                        )
                    )
                )
            }
        }
    }
}