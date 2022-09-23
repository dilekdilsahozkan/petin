package com.moralabs.pet.settings.presentation.ui.privacyAndSecurity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentBlockedAccountsBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.presentation.adapter.BlockedAccountsAdapter
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlockedAccountsFragment() : BaseFragment<FragmentBlockedAccountsBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_blocked_accounts
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_privacy_and_security))
    }

    private val blockedAccountsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BlockedAccountsAdapter(
            buttonClick = {
                it.userId?.let { userId ->
                    viewModel.unBlock(userId)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.blockedAccountsRecyclerView.adapter = blockedAccountsAdapter
        viewModel.getBlockedAccounts()
        viewModel.logout()

    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.stateBlocked.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<List<BlockedDto>> -> {
                        blockedAccountsAdapter.submitList(it.data)
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.stateUnBlocked.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        viewModel.getBlockedAccounts()
                        stopLoading()
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                }
            }
        }
    }

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }
}