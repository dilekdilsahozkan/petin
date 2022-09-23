package com.moralabs.pet.settings.domain

import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.domain.AuthenticationUseCase
import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.repository.ProfileRepository
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.ChangePasswordRequestDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    private val profileRepository: ProfileRepository,
    private val authenticationUseCase: AuthenticationUseCase
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

    fun editUser(edit: EditUserDto): Flow<BaseResult<UserDto>> {
        return flow {
            settingRepository.editUser(edit).body()?.data?.let {
                emit(
                    BaseResult.Success(it)
                )
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