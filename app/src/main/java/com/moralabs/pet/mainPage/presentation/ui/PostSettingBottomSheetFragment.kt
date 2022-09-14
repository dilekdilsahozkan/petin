package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.FragmentPostSettingBottomSheetBinding

class PostSettingBottomSheetFragment(
    val listener: PostSettingBottomSheetListener,
    val postId: String?
) : BottomSheetDialogFragment(),
    View.OnClickListener {

    lateinit var binding: FragmentPostSettingBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_post_setting_bottom_sheet, null, false
        )

        binding.deletePost.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.deletePost -> {
                listener.onItemClick(postId)
                dismiss()
            }
        }
    }
}

interface PostSettingBottomSheetListener {
    fun onItemClick(postId: String?)
}
