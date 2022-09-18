package com.moralabs.pet.settings.presentation.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentEditPersonalInformationBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.EditUserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPersonalInformationFragment : BaseFragment<FragmentEditPersonalInformationBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_edit_personal_information
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_personal_information))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.editUser(
            EditUserDto(
                fullName = binding.fullNameEdit.text.toString(),
                phoneNumber = binding.phoneNumberEdit.text.toString()
            )
        )
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        binding.fullNameEdit.hint = data.fullName.toString()
        binding.phoneNumberEdit.hint = data.phoneNumber.toString()
        binding.userImage.loadImage(data.media?.url)
    }
}