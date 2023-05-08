package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.extension.hideKeyboard
import com.moralabs.pet.core.presentation.extension.isNotEmptyOrBlank
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentCommentBinding
import com.moralabs.pet.mainPage.presentation.adapter.CommentAdapter
import com.moralabs.pet.mainPage.presentation.viewmodel.CommentViewModel
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding, CommentDto, CommentViewModel>(),
    PostSettingBottomSheetListener {

    private val postId: String? by lazy {
        activity?.intent?.getStringExtra(CommentActivity.POST_ID)
    }
    private val userImage: String? by lazy {
        activity?.intent?.getStringExtra(CommentActivity.BUNDLE_USER_IMAGE)
    }
    private val userName: String? by lazy {
        activity?.intent?.getStringExtra(CommentActivity.BUNDLE_USER_NAME)
    }
    private val postType: Int? by lazy {
        activity?.intent?.getIntExtra(CommentActivity.BUNDLE_POST_TYPE, 0)
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

    private val commentAdapter by lazy {
        CommentAdapter(
            onButtonClick = {
                PostSettingBottomSheetFragment(
                    this,
                    it.id
                ).show(childFragmentManager, "")
            },
            onUserPhotoClick = {
                if (it.isCommentOwnedByUser != true) {
                    val bundle = bundleOf(
                        ProfileActivity.OTHER_USER_ID to it.user?.userId
                    )
                    val intent = Intent(context, ProfileActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            }
        )
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

        binding.writeComment.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text: String = binding.writeComment.text.toString()
                if (text.startsWith(" ")) {
                    binding.writeComment.setText(text.trim { it <= ' ' })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<*> -> {
                        viewModel.getComment(postId)
                    }
                    is ViewState.Error<*> -> {
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = commentAdapter

        viewModel.getComment(postId)

        when (postType) {
            TabTextType.POST_TYPE.type -> {
                binding.postIcon.setImageResource(R.drawable.ic_post)
                binding.postTypeText.text = context?.getString(R.string.post)
            }
            TabTextType.QAN_TYPE.type -> {
                binding.postIcon.setImageResource(R.drawable.ic_qna)
                binding.postTypeText.text = context?.getString(R.string.qna)
            }
        }
    }

    override fun stateSuccess(data: CommentDto) {
        super.stateSuccess(data)
        commentAdapter.submitList(data.comments)
        if(!data.content?.media.isNullOrEmpty()) {
            binding.postImage.loadImage(data.content?.media?.get(0)?.url)
        } else {
            binding.postImage.visibility = View.GONE
        }
        binding.postText.text = data.content?.text.toString()
        binding.userPhoto.loadImageWithPlaceholder(userImage)
        binding.username.text = userName
    }

    override fun onItemClick(postId: String?) {
        PetWarningDialog(
            requireContext(),
            PetWarningDialogType.CONFIRMATION,
            resources.getString(R.string.ask_sure),
            okay = getString(R.string.yes),
            discard = getString(R.string.no),
            description = resources.getString(R.string.delete_comment_warning),
            negativeButton = resources.getString(R.string.no),
            onResult = {
                if (PetWarningDialogResult.OK == it) {
                    viewModel.deleteComment(postId)
                }
            }
        ).show()
    }
}