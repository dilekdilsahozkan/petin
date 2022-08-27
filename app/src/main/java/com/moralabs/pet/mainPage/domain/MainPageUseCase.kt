package com.moralabs.pet.mainPage.domain

import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.ContentDto
import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.mainPage.data.repository.MainPageRepository
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainPageUseCase @Inject constructor(
    private val mainPageRepository: MainPageRepository
) : BaseUseCase() {

    fun getFeed(): Flow<BaseResult<PostDto>> {
        return flow {
            var feed = mainPageRepository.getFeed().body().let {
                PostDto(
                    id = it?.data?.id,
                    dateTime = it?.data?.dateTime,
                    likeCount = it?.data?.likeCount,
                    commentCount = it?.data?.commentCount,
                    isPostLikedByUser = it?.data?.isPostLikedByUser,
                    pageIndex = it?.data?.pageIndex,
                    user = it?.data?.user?.map {
                        UserInfoDto(
                            userId = it?.userId,
                            userName = it?.userName,
                            image = it?.image,
                        )
                    },
                    content = it?.data?.content?.map {
                        ContentDto(
                            media = it?.media,
                            text = it?.text,
                            type = it?.type,
                            location = it?.location?.map {
                                LocationDto(
                                    latitude = it?.latitude,
                                    longitude = it?.longitude
                                )
                            }
                        )
                    },
                    comments = it?.data?.comments?.map {
                        CommentDto(
                            id = it?.id,
                            user = it?.user,
                            text = it?.text,
                            commentCount = it?.commentCount,
                            isPostLikedByUser = it?.isPostLikedByUser,
                            pageIndex = it?.pageIndex,
                        )
                    }
                )
            }
            emit(
                BaseResult.Success(
                    feed
                )
            )
        }
    }
}