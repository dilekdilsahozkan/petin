package com.moralabs.pet.settings.presentation.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPersonalInformationBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInformationFragment : BaseFragment<FragmentPersonalInformationBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_personal_information
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_personal_information))
    }

    override fun addListeners() {
        super.addListeners()
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_personalInformationsFragment_to_editPersonalInformationsFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userInfo()
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        binding.emailEdit.text = data.email.toString()
        binding.usernameEdit.text = data.userName.toString()
        binding.fullNameEdit.text = data.fullName.toString()
        binding.phoneNumberEdit.text = data.phoneNumber.toString()
        binding.userImage.loadImageWithPlaceholder(data.media?.url)
    }
}