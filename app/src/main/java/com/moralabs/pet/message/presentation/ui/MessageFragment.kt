package com.moralabs.pet.message.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMessageBinding
import com.moralabs.pet.message.data.remote.dto.MessageDto
import com.moralabs.pet.message.presentation.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding, MessageDto, MessageViewModel>(){

    override fun getLayoutId() = R.layout.fragment_message
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<MessageDto> {
        val viewModel: MessageViewModel by viewModels()
        return viewModel    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.message))
    }
}