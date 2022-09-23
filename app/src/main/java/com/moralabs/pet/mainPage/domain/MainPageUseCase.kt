package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainPageUseCase @Inject constructor(
    private val postRepository: PostRepository,
) : BaseUseCase() {

    fun getFeed(searchQuery: String? = null): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    postRepository.getFeed(searchQuery).body()?.data ?: listOf()
                )
            )
        }
    }

    fun likePost(postId: String?): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    postRepository.likePost(postId).body()?.data ?: listOf()
                )
            )
        }
    }

    fun unlikePost(postId: String?): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    postRepository.unlikePost(postId).body()?.data ?: listOf()
                )
            )
        }
    }

    fun deletePost(postId: String?): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    postRepository.deletePost(postId).body()?.data ?: listOf()
                )
            )
        }
    }
}