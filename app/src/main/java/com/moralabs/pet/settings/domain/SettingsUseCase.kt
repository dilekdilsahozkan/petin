package com.moralabs.pet.settings.domain

import com.moralabs.pet.core.domain.BaseResult
import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) : BaseUseCase() {

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
                /*BaseResult.Success(
                    settingRepository.unBlock(userId).body()?.let {
                        it.success
                    } ?: false
                )*/
                BaseResult.Success(
                    settingRepository.unBlock(userId).isSuccessful
                )
            )
        }
    }
}