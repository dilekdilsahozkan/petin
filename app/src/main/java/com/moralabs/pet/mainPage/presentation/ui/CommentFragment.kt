package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.CommentsDto
import com.moralabs.pet.core.data.remote.dto.CreateCommentDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentCommentBinding
import com.moralabs.pet.databinding.ItemUserCommentBinding
import com.moralabs.pet.mainPage.data.remote.dto.CommentRequestDto
import com.moralabs.pet.mainPage.presentation.viewmodel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding, CreateCommentDto, CommentViewModel>() {

    private val postId: String? by lazy {
        activity?.intent?.getStringExtra(CommentActivity.POST_ID)
    }

    override fun getLayoutId() = R.layout.fragment_comment

    override fun fragmentViewModel(): BaseViewModel<CreateCommentDto> {
        val viewModel: CommentViewModel by viewModels()
        return viewModel
    }

     private val commentAdapter: BaseListAdapter<CommentsDto, ItemUserCommentBinding> by lazy {
        BaseListAdapter(R.layout.item_user_comment, BR.comment, onRowClick = {

        }, isSameDto = {oldItem, newItem ->
            true
        })
    }

    override fun addListeners() {
        super.addListeners()

        binding.commentSend.setOnClickListener {
            viewModel.writeComment(
                postId,
                CommentRequestDto(
                    text = binding.writeComment.text.toString()
                )
            )
        }
        viewModel.getComment(postId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = commentAdapter
    }

    override fun stateSuccess(data: CreateCommentDto) {
        super.stateSuccess(data)
        commentAdapter.submitList(data.userCommentValue?.comments)
    }
}