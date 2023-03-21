package com.moralabs.pet.mainPage.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.local.dao.PostDao
import com.moralabs.pet.core.data.local.entity.MediaEntity
import com.moralabs.pet.core.data.local.entity.PetAttributeEntity
import com.moralabs.pet.core.data.local.entity.PostEntity
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.ContentDto
import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.PostRepository
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainPageUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val postDao: PostDao
) : BaseUseCase() {

    fun getFeed(searchQuery: String? = null, postType: Int? = null, lastDateTime: Long? = null): Flow<BaseResult<List<PostDto>>> {
        return flow {
            // TODO : Get Posts from cache

            val feed = postRepository.getFeed(searchQuery = searchQuery, postType = postType, dateTime = lastDateTime)

            if (feed.isSuccessful && feed.code() == 200) {
                feed.body()?.data?.map {
                    it.toPostEntity()
                }?.let {
                    postDao.upsertPosts(it)
                }

                emit(
                    BaseResult.Success(
                        feed.body()?.data ?: mutableListOf()
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
            postId?.let {
                postDao.setIsMyLike(it, true)
                postDao.incrementLikeCount(it)
            }

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
            postId?.let {
                postDao.setIsMyLike(it, false)
                postDao.descrementLikeCount(it)
            }

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
            postId?.let {
                postDao.deletePost(it)
            }

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

fun PostDto.toPostEntity() = PostEntity(
    id = this.id,
    dateTime = this.dateTime,
    userId = this.user?.userId,
    userName = this.user?.userName,
    userImage = this.user?.media?.url,
    contentText = this.content?.text,
    likeCount = this.likeCount,
    commentCount = this.commentCount,
    offerCount = this.offerCount,
    isOfferAvailableByUser = this.isOfferAvailableByUser,
    isPostLikedByUser = this.isPostLikedByUser,
    isPostOwnedByUser = this.isPostOwnedByUser,
    petName = this.content?.pet?.name,
    petMediaUrl = this.content?.pet?.media?.url,
    petAttributes = this.content?.pet?.petAttributes?.map {
        PetAttributeEntity(
            attributeType = it.attributeType,
            choice = it.choice,
            postId = this.id
        )
    },
    locationCity = this.content?.location?.city,
    contentMediaEntity = this.content?.media?.filter { it.id.isNullOrEmpty().not() }
        ?.map { media -> MediaEntity(media.id ?: "", this.id, media.url) },
    contentType = this.content?.type
)

fun PostEntity.toPostDto() = PostDto(
    id = this.id,
    dateTime = this.dateTime,
    user = UserInfoDto(
        userId = this.userId,
        userName = this.userName,
        media = MediaDto(
            url = this.userImage
        )
    ),
    content = ContentDto(
        text = this.contentText,
        pet = PetDto(
            name = this.petName,
            media = MediaDto(
                url = this.petMediaUrl
            ),
            petAttributes = this.petAttributes?.map { PetAttributeDto(attributeType = it?.attributeType, choice = it?.choice) }
        ),
        location = LocationDto(
            city = this.locationCity
        ),
        type = this.contentType,
        media = this.contentMediaEntity?.map { media -> MediaDto(id = media.id, url = media.url) }
    ),
    likeCount = this.likeCount,
    commentCount = this.commentCount,
    offerCount = this.offerCount,
    isOfferAvailableByUser = this.isOfferAvailableByUser,
    isPostLikedByUser = this.isPostLikedByUser,
    isPostOwnedByUser = this.isPostOwnedByUser,
)