package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNewPostBinding
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.presentation.viewmodel.NewPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostFragment : BaseFragment<FragmentNewPostBinding, NewPostDto, NewPostViewModel>() {

    private val postType: Int? by lazy {
        activity?.intent?.getIntExtra(NewPostActivity.BUNDLE_CHOOSE_TYPE, 0)
    }

    override fun getLayoutId() = R.layout.fragment_new_post
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<NewPostDto> {
        val viewModel: NewPostViewModel by viewModels()
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.newPost))
    }
}