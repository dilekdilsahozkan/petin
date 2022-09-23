package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.CommentsDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.extension.hideKeyboard
import com.moralabs.pet.core.presentation.extension.isNotEmptyOrBlank
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentCommentBinding
import com.moralabs.pet.databinding.ItemUserCommentBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.CommentViewModel
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding, CommentDto, CommentViewModel>() {

    private val postId: String? by lazy {
        activity?.intent?.getStringExtra(CommentActivity.POST_ID)
    }

    override fun getLayoutId() = R.layout.fragment_comment

    override fun fragmentViewModel(): BaseViewModel<CommentDto> {
        val viewModel: CommentViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.comments))
    }

    private val commentAdapter: BaseListAdapter<CommentsDto, ItemUserCommentBinding> by lazy {
        BaseListAdapter(R.layout.item_user_comment, BR.comment, onRowClick = {
            if (it.isCommentOwnedByUser != true) {
                val bundle = bundleOf(
                    ProfileActivity.OTHER_USER_ID to it.user?.userId
                )
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            }
        })
    }

    override fun addListeners() {
        super.addListeners()

        binding.commentSend.setEndIconOnClickListener {
            if (binding.writeComment.text.toString().isNotEmptyOrBlank()) {
                viewModel.writeComments(postId, binding.writeComment.text.toString()) {
                    binding.writeComment.text = null
                    binding.writeComment.clearFocus()
                    hideKeyboard()
                    viewModel.getComment(postId)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = commentAdapter

        viewModel.getComment(postId)
    }

    override fun stateSuccess(data: CommentDto) {
        super.stateSuccess(data)
        commentAdapter.submitList(data.comments)
    }
}