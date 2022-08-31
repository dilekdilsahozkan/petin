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
            profileRepository?.userInfo()?.body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }

        }
    }
}