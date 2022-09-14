package com.moralabs.pet.settings.domain

import com.moralabs.pet.core.domain.BaseUseCase
import com.moralabs.pet.settings.data.repository.SettingRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    settingRepository: SettingRepository
) : BaseUseCase() {
}