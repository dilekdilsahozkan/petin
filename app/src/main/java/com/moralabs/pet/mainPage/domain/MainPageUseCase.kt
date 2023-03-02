package com.moralabs.pet.mainPage.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainPageUseCase @Inject constructor(
    private val postRepository: PostRepository,
) : BaseUseCase() {

    fun getFeed(searchQuery: String? = null, postType: Int? = null): Flow<BaseResult<List<PostDto>>> {
        return flow {
            val feed = postRepository.getFeed(searchQuery, postType)
            if (feed.isSuccessful && feed.code() == 200) {
                emit(
                    BaseResult.Success(
                        feed.body()?.data ?: listOf()
                    )
                )
            } else {
                val error = Gson().fromJson(feed.errorBody()?.string(), BaseResponse::class.java)
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
            val unlike = postRepository.unlikePost(postId)
            if (unlike.isSuccessful && unlike.code() == 200) {
                emit(
                    BaseResult.Success(true)
                )
            } else {
                val error = Gson().fromJson(unlike.errorBody()?.string(), BaseResponse::class.java)
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

    fun reportPost(postId: String?, reportType: Int?): Flow<BaseResult<Boolean>> {
        return flow {
            val report = postRepository.reportPost(postId, reportType)
            if (report.isSuccessful && report.code() == 200) {
                emit(
                    BaseResult.Success(true)
                )
            } else {
                val error = Gson().fromJson(report.errorBody()?.string(), BaseResponse::class.java)
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