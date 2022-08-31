package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import retrofit2.Response

interface ProfileRepository {
    suspend fun userInfo(): Response<BaseResponse<UserDto>>
}