package com.moralabs.pet.newPost.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.PostDto
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNewPostBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.presentation.viewmodel.NewPostViewModel
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_post.*
import kotlinx.android.synthetic.main.fragment_new_post.view.*

@AndroidEntryPoint
class NewPostFragment : BaseFragment<FragmentNewPostBinding, PostDto, NewPostViewModel>() {

    private val postType: Int? by lazy {
        activity?.intent?.getIntExtra(NewPostActivity.BUNDLE_CHOOSE_TYPE, 0)
    }

    override fun getLayoutId() = R.layout.fragment_new_post
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<PostDto> {
        val viewModel: NewPostViewModel by viewModels()
        return viewModel
    }

    private val petCardAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = {

         binding.petList.setBackgroundColor(R.drawable.background_stroke_soft_orange)

        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun addListeners() {
        super.addListeners()
        binding.toolbar.publishText.setOnClickListener{
            viewModel.createPost(
                NewPostDto(
                    media = listOf(MediaDto()),
                    text = it.explanationText.text.toString(),
                    location = LocationDto()
                )
            )
        }
        binding.toolbar.imgClose.setOnClickListener {
            startActivity(Intent(context, MainPageActivity::class.java))
        }
    }

    override fun stateSuccess(data: PostDto) {
        super.stateSuccess(data)
        startActivity(Intent(context, MainPageActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.petList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = petCardAdapter
            setHasFixedSize(true)
        }

        if (postType == TabTextType.POST_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
        }
        if (postType == TabTextType.QAN_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
            binding.postIcon.setImageResource(R.drawable.ic_qna)
            binding.postText.text = getString(R.string.qna)
        }
        if (postType == TabTextType.FIND_PARTNER_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.postIcon.setImageResource(R.drawable.ic_partner)
            binding.postText.text = getString(R.string.findPartner)
        }
        if (postType == TabTextType.ADOPTION_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.postIcon.setImageResource(R.drawable.ic_adoption)
            binding.postText.text = getString(R.string.adoption)
        }
    }
}