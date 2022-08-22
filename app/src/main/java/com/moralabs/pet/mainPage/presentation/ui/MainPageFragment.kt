package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.data.remote.dto.ContentTypeDto
import com.moralabs.pet.mainPage.data.remote.dto.PostDto
import com.moralabs.pet.mainPage.presentation.adapter.PostListAdapter
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, PostDto, MainPageViewModel>(){

    lateinit var postAdapter: PostListAdapter
    var list = mutableListOf<ContentTypeDto>()
    override fun getLayoutId() = R.layout.fragment_main_page
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PostDto> {
        val viewModel: MainPageViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        postAdapter = PostListAdapter()
        recyclerview.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}