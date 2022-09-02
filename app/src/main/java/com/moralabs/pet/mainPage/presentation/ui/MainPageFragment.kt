package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, List<PostDto>, MainPageViewModel>() {

    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<PostDto>> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    private val postAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PostListAdapter(
            onOfferClick = {
                startActivity(Intent(context, OfferActivity::class.java))
            },
            onLikeClick = {

            },
            onCommentClick = {
                findNavController().navigate(R.id.action_post_to_comment)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postAdapter
            setHasFixedSize(true)
        }
        viewModel.feedPost()
    }
    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)
        postAdapter.submitList(data)
    }
}