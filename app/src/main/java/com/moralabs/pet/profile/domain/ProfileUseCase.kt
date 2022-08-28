package com.moralabs.pet.profile.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseUseCase() {

    fun userInfo(): Flow<BaseResult<UserDto>>{
        return flow{
            var userInfo = profileRepository.userInfo().body().let {
                UserDto(
                    id = it?.data?.id,
                    image = it?.data?.image,
                    userName = it?.data?.userName,
                    fullName = it?.data?.fullName,
                    email = it?.data?.email,
                    phoneNumber = it?.data?.phoneNumber,
                    birthDate = it?.data?.birthDate,
                    followerCount = it?.data?.followerCount,
                    followedCount = it?.data?.followedCount,
                    postCount = it?.data?.postCount,
                    pageIndex = it?.data?.pageIndex
                )
            }
            emit(
                BaseResult.Success(
                    userInfo
                )
            )
        }
    }
}