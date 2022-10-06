package com.moralabs.pet.onboarding.presentation.ui.guest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.extension.isEmptyOrBlank
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentGuestBinding
import com.moralabs.pet.mainPage.presentation.ui.CommentActivity
import com.moralabs.pet.mainPage.presentation.ui.PostSettingBottomSheetFragment
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import com.moralabs.pet.offer.presentation.ui.MakeOfferActivity
import com.moralabs.pet.offer.presentation.ui.OfferUserActivity
import com.moralabs.pet.petProfile.presentation.ui.PetProfileActivity
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestFragment : BaseFragment<FragmentGuestBinding, List<PostDto>, MainPageViewModel>() {

    override fun getLayoutId() = R.layout.fragment_guest
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    private val postAdapter by lazy {
        PostListAdapter(
            onOfferClick = {
                    val bundle = bundleOf(
                        MakeOfferActivity.POST_ID to it.id,
                        MakeOfferActivity.OFFER_TYPE to it.content?.type
                    )
                    val intent = Intent(context, MakeOfferActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
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
                    if (it.isPostLikedByUser == true) {
                        viewModel.unlikePost(it.id)
                        viewModel.feedPost()
                    } else {
                        viewModel.likePost(it.id)
                        viewModel.feedPost()
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
            onOfferUserClick = {
                    if (it.isPostOwnedByUser == true) {
                        val bundle = bundleOf(
                            OfferUserActivity.POST_ID to it.id
                        )
                        val intent = Intent(context, OfferUserActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
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

    override fun addListeners() {
        super.addListeners()

        binding.searchEdittext.addTextChangedListener {
            if (it.toString().isEmptyOrBlank()) {
                viewModel.feedPost()
            } else {
                viewModel.feedPost(it.toString())
            }
        }
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)
        postAdapter.submitList(data)
    }
}