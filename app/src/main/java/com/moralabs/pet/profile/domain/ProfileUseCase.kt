package com.moralabs.pet.profile.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.core.domain.ErrorCode
import com.moralabs.pet.core.domain.ErrorResult
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import com.moralabs.pet.profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseUseCase() {

    fun userInfo(): Flow<BaseResult<UserDto>> {
        return flow {
            profileRepository.userInfo().body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun otherUsersInfo(userId: String?): Flow<BaseResult<UserDto>> {
        return flow {
            profileRepository.otherUsersInfo(userId).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun getFollowedList(): Flow<BaseResult<List<UserInfoDto>>> {
        return flow {
            profileRepository.getFollowedList().body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun getFollowerList(): Flow<BaseResult<List<UserInfoDto>>> {
        return flow {
            profileRepository.getFollowerList().body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun followUser(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    profileRepository.followUser(userId).body()?.let {
                        it.success
                    } ?: false
                )
            )
        }
    }

    fun unfollowUser(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    profileRepository.unfollowUser(userId).body()?.let {
                        it.success
                    } ?: false
                )
            )
        }
    }

    fun blockUser(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    profileRepository.blockUser(userId).body()?.let {
                        it.success
                    } ?: false
                )
            )
        }
    }

    fun unblockUser(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    profileRepository.unblockUser(userId).body()?.let {
                        it.success
                    } ?: false
                )
            )
        }
    }

    fun reportUser(userId: String?, reportType: Int?): Flow<BaseResult<Boolean>> {
        return flow {
            val report = profileRepository.reportUser(userId, reportType)
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
}