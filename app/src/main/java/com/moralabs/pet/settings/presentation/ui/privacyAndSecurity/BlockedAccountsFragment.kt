package com.moralabs.pet.settings.presentation.ui.privacyAndSecurity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentBlockedAccountsBinding
import com.moralabs.pet.databinding.ItemBlockedAccountsBinding
import com.moralabs.pet.settings.data.remote.dto.BlockedDto
import com.moralabs.pet.settings.data.remote.dto.SettingsDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockedAccountsFragment : BaseFragment<FragmentBlockedAccountsBinding, SettingsDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_blocked_accounts
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_privacy_and_security))
    }

    private val blockedAccountsAdapter: BaseListAdapter<BlockedDto, ItemBlockedAccountsBinding> by lazy {
        BaseListAdapter(R.layout.item_blocked_accounts, BR.item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.blockedAccountsRecyclerView.adapter = blockedAccountsAdapter
        viewModel.getBlockedAccounts()
    }

    override fun fragmentViewModel(): BaseViewModel<SettingsDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }
}