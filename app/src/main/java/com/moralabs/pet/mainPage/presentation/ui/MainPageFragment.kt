package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main_page.*

@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, List<PostDto>, MainPageViewModel>() {

    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    private val postAdapter by lazy {
        PostListAdapter(
            onOfferClick = {
                val bundle = bundleOf(
                    OfferActivity.POST_ID to it.id
                )
                val intent = Intent(context, OfferActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            },
            onLikeClick = {
                val postId = it.id
                viewModel.likePost(postId)
                viewModel.feedPost()
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
                val bundle = bundleOf(
                    OfferUserActivity.POST_ID to it.id
                )
                val intent = Intent(context, OfferUserActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            },
            onUserPhotoClick = {
                if (it.isPostOwnedByUser != true) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = postAdapter

        viewModel.feedPost()
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
    }
}