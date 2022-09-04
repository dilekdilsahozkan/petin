package com.moralabs.pet.mainPage.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.google.android.material.navigation.NavigationView
import com.moralabs.pet.R
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.PostListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.CurvedBottomNavigationView
import com.moralabs.pet.databinding.FragmentMainPageBinding
import com.moralabs.pet.mainPage.presentation.viewmodel.MainPageViewModel
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainPageFragment : BaseFragment<FragmentMainPageBinding, List<PostDto>, MainPageViewModel>(),
    NavigationView.OnNavigationItemSelectedListener {

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
                val bundle = bundleOf(
                    CommentActivity.POST_ID to id
                )
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            }
        )
    }

    override fun addListeners() {
        super.addListeners()

        val navBar: CurvedBottomNavigationView? = activity?.findViewById(R.id.dashboard_navigation)
        val addButton: ImageButton? = activity?.findViewById(R.id.addPostButton)

        binding.filterIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            navBar?.visibility = View.GONE
            addButton?.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = postAdapter
        binding.filterNavigation.setNavigationItemSelectedListener(this)

        viewModel.feedPost()
    }

    override fun stateSuccess(data: List<PostDto>) {
        super.stateSuccess(data)

        postAdapter.submitList(data)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        }
        return true
    }
}