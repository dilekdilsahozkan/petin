package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.extension.isEmptyOrBlank
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import com.moralabs.pet.newPost.presentation.ui.TabTextType
import com.moralabs.pet.notification.presentation.ui.NotificationActivity
import com.moralabs.pet.offer.presentation.ui.MakeOfferActivity
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.petProfile.presentation.ui.PetProfileActivity
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, List<PostDto>, MainPageViewModel>(),
    PostSettingBottomSheetListener, PostReportBottomSheetListener, FilterBottomSheetListener {

    var reportedPostId = ""
    var postType = 0

    companion object {
        var instance: MainPageFragment? = null
    }

    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
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
            onPetProfile = {
                if (it.isPostOwnedByUser != true) {
                    val bundle = bundleOf(
                        PetProfileActivity.PET_ID to it.content?.pet?.id,
                        PetProfileActivity.OTHER_USER_ID to it.user?.userId
                    )
                    val intent = Intent(context, PetProfileActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            },
            onLikeClick = {
                loginIfNeeded {
                    if (it.isPostLikedByUser == true) {
                        viewModel.unlikePost(it.id)
                    } else {
                        viewModel.likePost(it.id)
                    }

                    it.isPostLikedByUser = it.isPostLikedByUser?.not() ?: true
                    it.likeCount =
                        (it.likeCount ?: 0) + (if (it.isPostLikedByUser == true) 1 else -1)
                }
            },
            onCommentClick = {
                loginIfNeeded {
                    val bundle = bundleOf(
                        CommentActivity.POST_ID to it.id
                    )
                    val intent = Intent(context, CommentActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
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
            onUserPhotoClick = {
                loginIfNeeded {
                    if (it.isPostOwnedByUser != true) {
                        val bundle = bundleOf(
                            ProfileActivity.OTHER_USER_ID to it.user?.userId
                        )
                        val intent = Intent(context, ProfileActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
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
        val paddingDp = 15
        val density = context?.resources?.displayMetrics?.density
        var paddingPixel = 0f
        density?.let {
            paddingPixel = it * paddingDp
        }
        binding.searchEdittext.setPadding(paddingPixel.toInt(), 0, 0, 0)

        binding.filterIcon.setOnClickListener {
            loginIfNeeded {
                FilterBottomSheetFragment(
                    this
                ).show(childFragmentManager, "")
            }
        }
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
        postAdapter.notifyDataSetChanged()

        binding.refreshLayout.isRefreshing = false
    }

    override fun stateError(data: String?) {
        super.stateError(data)

        binding.refreshLayout.isRefreshing = false

        if (postAdapter.currentList.size > 0 &&
            postAdapter.currentList[postAdapter.currentList.size - 1].user == null
        ) {
            var list = postAdapter.currentList.toMutableList()
            list.removeLast()
            postAdapter.submitList(list)
        }
        binding.recyclerview.smoothScrollToPosition(0)
    }

    override fun addListeners() {
        super.addListeners()

        var textChangedListenerBlock = true
        binding.searchEdittext.addTextChangedListener {
            if (textChangedListenerBlock.not()) {
                feedPost(true)
            }
            textChangedListenerBlock = false
        }

        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<Boolean> -> {
                        feedPost(true)
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                    else -> {}
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            feedPost(true)
        }

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (recyclerView.canScrollVertically(1).not() &&
                    postAdapter.currentList.size > 0 &&
                    postAdapter.currentList[postAdapter.currentList.size - 1].user != null
                ) {
                    var list = postAdapter.currentList.toMutableList()
                    list.add(PostDto())
                    postAdapter.submitList(list)
                    feedPost()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setFragmentResultListener(
            "fragment_location"
        ) { _, result ->
            result.getString("locationId")
        }
    }

    override fun onStart() {
        super.onStart()

        feedPost(true)
    }

    override fun startLoading() {
        if (postAdapter.currentList.isEmpty()) {
            super.startLoading()
        }
    }

    private fun feedPost(forceReload: Boolean = false) {
        viewModel.postType.observe(this){ postType ->
            if(postType == -1){
                viewModel.feedPost()
            }
            else if (binding.searchEdittext.text.toString().isEmptyOrBlank()) {
                viewModel.feedPost(forceReload, postType = postType)
            } else {
                viewModel.feedPost(forceReload, binding.searchEdittext.text.toString(), postType)
            }
        }
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

    fun scrollToTop() {
        binding.recyclerview.smoothScrollToPosition(0)
    }

    override fun onFilterClick(postType: Int) {
        when (postType) {
            0 -> TabTextType.POST_TYPE.type
            1 -> TabTextType.QAN_TYPE.type
            2 -> TabTextType.FIND_PARTNER_TYPE.type
            3 -> TabTextType.ADOPTION_TYPE.type
            4 -> TabTextType.ALL_POST.type
            else -> 4
        }

        viewModel.postType.value = postType

        when (postType) {
            TabTextType.POST_TYPE.type -> {
                viewModel.feedPost(postType = 0)
                binding.filterType.setImageResource(R.drawable.ic_filter_post)
                binding.filterType.visibility = View.VISIBLE
            }
            TabTextType.QAN_TYPE.type -> {
                viewModel.feedPost(postType = 1)
                binding.filterType.setImageResource(R.drawable.ic_filter_qna)
                binding.filterType.visibility = View.VISIBLE
            }
            TabTextType.FIND_PARTNER_TYPE.type -> {
                viewModel.feedPost(postType = 2)
                binding.filterType.setImageResource(R.drawable.ic_filter_partner)
                binding.filterType.visibility = View.VISIBLE
            }
            TabTextType.ADOPTION_TYPE.type -> {
                viewModel.feedPost(postType = 3)
                binding.filterType.setImageResource(R.drawable.ic_filter_adoption)
                binding.filterType.visibility = View.VISIBLE
            }
            else -> {
                viewModel.feedPost()
                binding.filterType.visibility = View.GONE
            }
        }
    }
}