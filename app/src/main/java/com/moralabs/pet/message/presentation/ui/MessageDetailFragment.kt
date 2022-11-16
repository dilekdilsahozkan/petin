package com.moralabs.pet.message.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.extension.*
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMessageDetailBinding
import com.moralabs.pet.databinding.ItemChatMessageBinding
import com.moralabs.pet.message.data.remote.dto.ChatDto
import com.moralabs.pet.message.presentation.model.UiChatMessageDto
import com.moralabs.pet.message.presentation.model.UiChatMessageType
import com.moralabs.pet.message.presentation.viewmodel.MessageDetailViewModel
import com.moralabs.pet.profile.data.remote.dto.UserDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageDetailFragment : BaseFragment<FragmentMessageDetailBinding, ChatDto, MessageDetailViewModel>() {

    override fun getLayoutId() = R.layout.fragment_message_detail
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<ChatDto> {
        val viewModel: MessageDetailViewModel by viewModels()
        return viewModel
    }

    private val messageAdapter: BaseListAdapter<UiChatMessageDto, ItemChatMessageBinding> by lazy {
        BaseListAdapter(R.layout.item_chat_message, BR.item)
    }

    private val userDto by lazy {
        activity?.intent?.getParcelableExtra<UserDto>(MessageDetailActivity.BUNDLE_USER)
    }

    private val userId by lazy {
        activity?.intent?.getStringExtra(MessageDetailActivity.USER_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = messageAdapter
        (binding.recyclerView.layoutManager as? LinearLayoutManager)?.stackFromEnd = true

        userDto?.userId?.let{
            viewModel.getDetail(userDto?.userId)
        } ?: run {
            viewModel.getDetail(userId)
        }
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(userDto?.fullName)
    }

    override fun addListeners() {
        super.addListeners()

        binding.messageTextLayout.setEndIconOnClickListener {
            if (binding.messageText.text.toString().isNotEmptyOrBlank()) {
                viewModel.sendMessage(userDto?.userId, binding.messageText.text.toString()) {
                    binding.messageText.text = null
                    binding.messageText.clearFocus()
                    hideKeyboard()
                    viewModel.getDetail(userDto?.userId)
                }
            }
        }

        binding.messageText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text: String = binding.messageText.text.toString()
                if (text.startsWith(" ")) {
                    binding.messageText.setText(text.trim { it <= ' ' })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun stateSuccess(data: ChatDto) {
        super.stateSuccess(data)

        val uiList = mutableListOf<UiChatMessageDto>()

        if(data.isUserBlocked == true){
            binding.messageTextLayout.visibility = View.GONE
            Toast.makeText(requireContext(), getString(R.string.no_user), Toast.LENGTH_LONG).show()
        }else{
            binding.messageTextLayout.visibility = View.VISIBLE
        }

        data.messages?.groupBy {
            it.dateTime.toDbDate()
        }?.forEach {
            uiList.add(
                UiChatMessageDto(
                    text = it.key.dbToFullDate(context),
                    type = UiChatMessageType.TITLE
                )
            )
            it.value.forEach { message ->
                uiList.add(
                    UiChatMessageDto(
                        text = message.text,
                        type = if (message.isUser == true) UiChatMessageType.OUTGOING_MESSAGE else UiChatMessageType.INCOMING_MESSAGE,
                        hour = message.dateTime.toHour()
                    )
                )
            }
        }
        messageAdapter.submitList(uiList)

        (activity as? MessageDetailActivity)?.setUser(data.to)
    }
}