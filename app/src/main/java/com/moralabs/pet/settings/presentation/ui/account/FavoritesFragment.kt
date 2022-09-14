package com.moralabs.pet.settings.presentation.ui.account

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentFavoritesBinding
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_favorites
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_my_favorites))
    }

    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }
}