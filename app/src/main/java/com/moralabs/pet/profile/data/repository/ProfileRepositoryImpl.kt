package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.profile.data.remote.api.UserService
import com.moralabs.pet.profile.data.remote.dto.UserDto
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val service: UserService) :
    ProfileRepository, BaseRepository {
    override suspend fun userInfo(): Response<BaseResponse<UserDto>> = service.userInfo()
}
