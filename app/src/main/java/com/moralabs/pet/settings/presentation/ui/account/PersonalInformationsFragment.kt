package com.moralabs.pet.settings.presentation.ui.account

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentPersonalInformationsBinding
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInformationsFragment : BaseFragment<FragmentPersonalInformationsBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_personal_informations
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun addListeners() {
        super.addListeners()
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_personalInformationsFragment_to_editPersonalInformationsFragment)
        }
    }


    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_personal_informations))
    }


    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

}