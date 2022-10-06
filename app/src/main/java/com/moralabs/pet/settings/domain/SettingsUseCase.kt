package com.moralabs.pet.settings.domain

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
            profileRepository.userInfo().body()?.data?.let {
                emit(
                    BaseResult.Success(it)
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

            val result = settingRepository.editUser(editUserDto).body()?.success ?: false
            if (result) {
                settingRepository.editUser(editUserDto).body()?.data?.let {
                    emit(
                        BaseResult.Success(it)
                    )
                }
            } else {
                emit(BaseResult.Error(ErrorResult(code = ErrorCode.SERVER_ERROR)))
            }

        }
    }

    fun getBlockedAccounts(): Flow<BaseResult<List<BlockedDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    settingRepository.getBlockedAccounts().body()?.data ?: listOf()
                )
            )
        }
    }

    fun unBlock(userId: String?): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    settingRepository.unBlock(userId).isSuccessful
                )
            )
        }
    }

    fun getLikedPosts(): Flow<BaseResult<List<PostDto>>> {
        return flow {
            emit(
                BaseResult.Success(
                    settingRepository.getLikedPosts().body()?.data ?: listOf()
                )
            )
        }
    }

    fun changePassword(refreshToken: String, changePassword: ChangePasswordRequestDto): Flow<BaseResult<Boolean>> {
        return flow {
            emit(
                BaseResult.Success(
                    settingRepository.changePassword(refreshToken, changePassword).body() ?: false
                )
            )
        }
    }

    fun getInfo(infoType: Int): Flow<BaseResult<String>> {
        return flow {
            settingRepository.getInfo(infoType).body()?.data?.let {
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

    fun logout() {
        authenticationUseCase.logout()
    }
}