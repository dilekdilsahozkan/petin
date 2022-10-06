package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.databinding.FragmentPostBinding
import com.moralabs.pet.mainPage.presentation.ui.CommentActivity
import com.moralabs.pet.mainPage.presentation.ui.PostSettingBottomSheetFragment
import com.moralabs.pet.mainPage.presentation.ui.PostSettingBottomSheetListener
import com.moralabs.pet.profile.presentation.viewmodel.ProfilePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding, List<PostDto>, ProfilePostViewModel>(),
    PostSettingBottomSheetListener {

    private val otherUserId: String? by lazy {
        activity?.intent?.getStringExtra(ProfileActivity.OTHER_USER_ID)
    }

    override fun getLayoutId() = R.layout.fragment_post
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: ProfilePostViewModel by viewModels()
        return viewModel
    }

    private val postAdapter by lazy {
        PostListAdapter(
            onLikeClick = {
                if (it.isPostLikedByUser == true) {
                    viewModel.unlikePost(it.id)
                } else {
                    viewModel.likePost(it.id)
                }
            },
            onCommentClick = {
                val bundle = bundleOf(
                    CommentActivity.POST_ID to it.id
                )
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            },
            onPostSettingClick = {
                fragmentManager?.let { it1 ->
                    PostSettingBottomSheetFragment(
                        this,
                        it.id
                    ).show(it1, "")
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = postAdapter

        if (!otherUserId.isNullOrEmpty()) {
            viewModel.getPostAnotherUser(otherUserId)
        } else {
            viewModel.profilePost()
        }
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
    }

    override fun onItemClick(postId: String?) {

        PetWarningDialog(
            requireContext(),
            PetWarningDialogType.CONFIRMATION,
            resources.getString(R.string.ask_sure),
            okay = getString(R.string.yes),
            description = resources.getString(R.string.delete_post_warning),
            negativeButton = resources.getString(R.string.no),
            onResult = {
                if (PetWarningDialogResult.OK == it) {
                    viewModel.deletePost(postId)
                }
            }
        ).show()
    }
}