package com.moralabs.pet.message.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMessageBinding
import com.moralabs.pet.databinding.ItemUserMessageBinding
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.presentation.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding, List<ChatDto>, MessageViewModel>() {

    override fun getLayoutId() = R.layout.fragment_message

    private val messageAdapter: BaseListAdapter<ChatDto, ItemUserMessageBinding> by lazy {
        BaseListAdapter(R.layout.item_user_message, BR.item, onRowClick = {
            startActivity(
                Intent(
                    context,
                    MessageDetailActivity::class.java
                ).apply { putExtras(bundleOf(MessageDetailActivity.BUNDLE_USER to it.to)) })
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = messageAdapter
    }

    override fun addListeners() {
        super.addListeners()

        binding.toolbar.imgSelect.setOnClickListener {
            startActivity(Intent(context, MessageUserSearchActivity::class.java))
        }
    }

    override fun fragmentViewModel(): BaseViewModel<List<ChatDto>> {
        val viewModel: MessageViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.message))
    }

    override fun stateSuccess(data: List<ChatDto>) {
        super.stateSuccess(data)

        messageAdapter.submitList(data)
    }
}