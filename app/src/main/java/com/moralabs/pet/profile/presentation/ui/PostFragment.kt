package com.moralabs.pet.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.*
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentPostBinding
import com.moralabs.pet.mainPage.presentation.ui.*
import com.moralabs.pet.offer.presentation.ui.MakeOfferActivity
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.profile.presentation.viewmodel.ProfilePostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding, List<PostDto>, ProfilePostViewModel>(),
    PostSettingBottomSheetListener, PostReportBottomSheetListener {

    var reportedPostId = ""

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
            onOfferClick = {
                loginIfNeeded {
                    val bundle = bundleOf(
                        MakeOfferActivity.POST_ID to it.id,
                        MakeOfferActivity.OFFER_TYPE to it.content?.type
                    )
                    val intent = Intent(context, MakeOfferActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            },
            onLikeClick = {
                if (it.isPostLikedByUser == true) {
                    viewModel.unlikePost(it.id)
                } else {
                    viewModel.likePost(it.id)
                }

                it.isPostLikedByUser = it.isPostLikedByUser?.not() ?: true
                it.likeCount = (it.likeCount ?: 0) + (if(it.isPostLikedByUser == true) 1 else -1)

            },
            onCommentClick = {
                val bundle = bundleOf(
                    CommentActivity.POST_ID to it.id
                )
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            },
            onOfferUserClick = {
                loginIfNeeded {
                    if (it.isPostOwnedByUser == true) {
                        val bundle = bundleOf(
                            OfferUserActivity.POST_ID to it.id
                        )
                        val intent = Intent(context, OfferUserActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                }
            },
            onContentImage = {
                it.content?.media?.get(0)?.url?.let { it1 ->
                    ImageViewerView(
                        requireContext(),
                        it1
                    ).show()
                }
            },
            onPostSettingClick = {
                loginIfNeeded {
                    if (it.isPostOwnedByUser == true) {
                        loginIfNeeded {
                            PostSettingBottomSheetFragment(
                                this,
                                it.id
                            ).show(childFragmentManager, "")
                        }
                    } else {
                        reportedPostId = it.id
                        loginIfNeeded {
                            PostReportBottomSheetFragment(
                                this,
                                it.id
                            ).show(childFragmentManager, "")
                        }
                    }
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

    override fun addListeners() {
        super.addListeners()

        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        viewModel.profilePost()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
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
            discard = getString(R.string.no),
            description = resources.getString(R.string.delete_post_warning),
            negativeButton = resources.getString(R.string.no),
            onResult = {
                if (PetWarningDialogResult.OK == it) {
                    viewModel.deletePost(postId)
                }
            }
        ).show()
    }

    override fun onReportClick(reportType: Int?) {
        PetWarningDialog(
            requireContext(),
            PetWarningDialogType.CONFIRMATION,
            resources.getString(R.string.ask_sure),
            okay = getString(R.string.yes),
            discard = getString(R.string.no),
            description = resources.getString(R.string.submit_report_warning),
            negativeButton = resources.getString(R.string.no),
            onResult = {
                if (PetWarningDialogResult.OK == it) {
                    Toast.makeText(requireContext(), getString(R.string.success_report), Toast.LENGTH_SHORT).show()
                    viewModel.reportPost(reportedPostId, reportType)
                }
            }
        ).show()
    }
}