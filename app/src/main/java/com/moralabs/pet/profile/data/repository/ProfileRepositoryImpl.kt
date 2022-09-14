package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.EmptyDto
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.profile.data.remote.api.UserService
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val service: UserService) :
    ProfileRepository, BaseRepository {
    override suspend fun userInfo(): Response<BaseResponse<UserDto>> = service.userInfo()
    override suspend fun otherUsersInfo(userId: String?): Response<BaseResponse<UserDto>> = service.otherUsersInfo(userId)
    override suspend fun getFollowedList(): Response<BaseResponse<List<UserInfoDto>>> = service.getFollowedList()
    override suspend fun followUser(userId: String?): Response<BaseResponse<*>> = service.followUser(userId)
    override suspend fun unfollowUser(userId: String?): Response<BaseResponse<*>> = service.unfollowUser(userId)
}
