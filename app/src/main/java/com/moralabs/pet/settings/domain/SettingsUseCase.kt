package com.moralabs.pet.settings.domain

import com.google.gson.Gson
import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.data.repository.MediaRepository
import com.moralabs.pet.core.domain.*
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.repository.ProfileRepository
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    private val profileRepository: ProfileRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val mediaRepository: MediaRepository
) : BaseUseCase() {

    fun userInfo(): Flow<BaseResult<UserDto>> {
        return flow {
            var user = profileRepository.userInfo()
            if(user.isSuccessful && user.code() == 200){
                user.body()?.data?.let {
                    emit(
                        BaseResult.Success(it)
                    )
                }
            }else{
                val error = Gson().fromJson(user.errorBody()?.string(), BaseResponse::class.java)
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

    fun editUser(fullName: String?, phoneNumber: String?, file: File?): Flow<BaseResult<UserDto>> {
        return flow {

            val editUserDto = EditUserDto(
                fullName = fullName,
                phoneNumber = phoneNumber
            )

            val medias = mutableListOf<MediaDto>()

            file?.let {
                val media = mediaRepository.uploadPhoto(1, it)

                media.body()?.data?.getOrNull(0)?.let {
                    medias.add(it)
                }
            }

            editUserDto.media = medias

            val result = settingRepository.editUser(editUserDto)

            if(result.isSuccessful && result.code() == 200){
                settingRepository.editUser(editUserDto).body()?.data?.let {
                    emit(
                        BaseResult.Success(it)
                    )
                }
            }else{
                val error = Gson().fromJson(result.errorBody()?.string(), BaseResponse::class.java)
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

    fun getBlockedAccounts(): Flow<BaseResult<List<BlockedDto>>> {
        return flow {
            val blocked = settingRepository.getBlockedAccounts()
            if(blocked.isSuccessful && blocked.code() == 200){
                emit(
                    BaseResult.Success(
                        blocked.body()?.data ?: listOf()
                    )
                )
            }else{
                val error = Gson().fromJson(blocked.errorBody()?.string(), BaseResponse::class.java)
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

    fun unBlock(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val unblock = settingRepository.unBlock(userId)
            emit(
                BaseResult.Success(
                    unblock.isSuccessful
                )
            )
        }
    }

    fun getLikedPosts(): Flow<BaseResult<List<PostDto>>> {
        return flow {
            val liked = settingRepository.getLikedPosts()
            emit(
                BaseResult.Success(
                    liked.body()?.data ?: listOf()
                )
            )
        }
    }

    fun changePassword(
        refreshToken: String,
        changePassword: ChangePasswordRequestDto
    ): Flow<BaseResult<Boolean>> {
        return flow {
            val change = settingRepository.changePassword(refreshToken, changePassword)
            if (change.isSuccessful && change.code() == 200) {
                emit(BaseResult.Success(true))
            } else {
                val error = Gson().fromJson(change.errorBody()?.string(), BaseResponse::class.java)
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

    fun getInfo(infoType: Int): Flow<BaseResult<String>> {
        return flow {
            val info = settingRepository.getInfo(infoType)
            info.body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
            }
        }
    }

    fun deleteAccount(): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    settingRepository.deleteAccount().body()?.success ?: false
                )
            )
        }
    }

    fun unlikePost(postId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            val unlike = settingRepository.unlikePost(postId)
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

    fun logout() {
        authenticationUseCase.logout()
    }
}