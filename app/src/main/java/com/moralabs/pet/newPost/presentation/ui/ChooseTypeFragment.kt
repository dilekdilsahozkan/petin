package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseTypeFragment : Fragment(),
    ChooseTypeBottomSheetListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { expertBottomSheetDialog.show(parentFragmentManager, tag) }
    }

    private val expertBottomSheetDialog: ChooseTypeBottomSheetFragment by lazy {
        ChooseTypeBottomSheetFragment(this)
    }

    override fun onItemClick(id: Int?) {
        when (id) {
            R.id.postText -> {
                findNavController().navigate(
                    R.id.action_fragment_choose_to_newPostFragment,
                )
            }
            R.id.qnaText -> {
                findNavController().navigate(
                    R.id.action_fragment_choose_to_newPostFragment,
                )
            }
            R.id.findPartnerText -> {
                findNavController().navigate(
                    R.id.action_fragment_choose_to_newPostFragment,
                )
            }
            R.id.adoptionText -> {
                findNavController().navigate(
                    R.id.action_fragment_choose_to_newPostFragment,
                )
            }
        }
    }
}