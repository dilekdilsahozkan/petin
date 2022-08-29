package com.moralabs.pet.settings.presentation.ui.account

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAccountBinding
import com.moralabs.pet.databinding.FragmentEditPersonalInformationsBinding
import com.moralabs.pet.databinding.FragmentPersonalInformationsBinding
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.ui.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPersonalInformationsFragment : BaseFragment<FragmentEditPersonalInformationsBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_edit_personal_informations
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH



    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_personal_informations))
    }


    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

}