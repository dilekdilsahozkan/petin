package com.moralabs.pet.message.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMessageUserSearchBinding
import com.moralabs.pet.databinding.ItemUserMessageInfoBinding
import com.moralabs.pet.message.presentation.model.UiChatUserDto
import com.moralabs.pet.message.presentation.viewmodel.MessageUserSearchViewModel
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  MessageUserSearchFragment :
    BaseFragment<FragmentMessageUserSearchBinding, List<UserDto>, MessageUserSearchViewModel>() {

    override fun getLayoutId() = R.layout.fragment_message_user_search
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    private val userAdapter: BaseListAdapter<UiChatUserDto, ItemUserMessageInfoBinding> by lazy {
        BaseListAdapter(R.layout.item_user_message_info, BR.item, onRowClick = { selected ->
            userAdapter.currentList.forEach {
                it.isSelected = it == selected
            }
            userAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = userAdapter
    }

    override fun fragmentViewModel(): BaseViewModel<List<UserDto>> {
        val viewModel: MessageUserSearchViewModel by viewModels()
        return viewModel
    }

    override fun addListeners() {
        super.addListeners()

        binding.searchEdittext.addTextChangedListener {
            viewModel.searchUser(it.toString())
        }

        binding.toolbar.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.toolbar.imgSelect.setOnClickListener {
            userAdapter.currentList.firstOrNull { it.isSelected }?.let {
                startActivity(
                    Intent(
                        context,
                        MessageDetailActivity::class.java
                    ).apply { putExtras(bundleOf(MessageDetailActivity.BUNDLE_USER to it.user)) })
            }
        }
    }

    override fun stateSuccess(data: List<UserDto>) {
        super.stateSuccess(data)

        userAdapter.submitList(data.map { UiChatUserDto(user = it) })
    }
}