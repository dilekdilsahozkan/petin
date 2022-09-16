package com.moralabs.pet.profile.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.profile.data.remote.api.UserService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val service: UserService) :
    ProfileRepository, BaseRepository {
    override suspend fun userInfo() = service.userInfo()
    override suspend fun otherUsersInfo(userId: String?) = service.otherUsersInfo(userId)
    override suspend fun getFollowedList() = service.getFollowedList()
    override suspend fun getFollowerList() = service.getFollowerList()
    override suspend fun followUser(userId: String?) = service.followUser(userId)
    override suspend fun unfollowUser(userId: String?) = service.unfollowUser(userId)
    override suspend fun getBlockedList() = service.getBlockedList()
    override suspend fun blockUser(userId: String?) = service.blockUser(userId)
    override suspend fun unblockUser(userId: String?) = service.unblockUser(userId)
}
