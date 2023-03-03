package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.ChooseTypeBottomSheetBinding
import com.moralabs.pet.databinding.FragmentFilterBottomSheetBinding
import com.moralabs.pet.newPost.presentation.ui.ChooseTypeBottomSheetListener
import com.moralabs.pet.newPost.presentation.ui.TabTextType

class FilterBottomSheetFragment (
    var listener: FilterBottomSheetListener,
) : BottomSheetDialogFragment(),
    View.OnClickListener {

    lateinit var binding: FragmentFilterBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_filter_bottom_sheet, null, false
        )
        binding.allPostText.setOnClickListener(this)
        binding.postText.setOnClickListener(this)
        binding.qnaText.setOnClickListener(this)
        binding.findPartnerText.setOnClickListener(this)
        binding.adoptionText.setOnClickListener(this)

        return binding.root
    }

    fun hide(){
        binding.allPostText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.postText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.qnaText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.findPartnerText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.adoptionText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.allPostText -> {
                hide()
                listener.onFilterClick(4)
                binding.allPostText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_select,0)
                dismiss()
            }
            R.id.postText -> {
                hide()
                listener.onFilterClick(0)
                binding.postText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_select,0)
                dismiss()
            }
            R.id.qnaText -> {
                hide()
                listener.onFilterClick(1)
                binding.qnaText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_select,0)
                dismiss()
            }
            R.id.findPartnerText -> {
                hide()
                listener.onFilterClick(2)
                binding.findPartnerText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_select,0)
                dismiss()
            }
            R.id.adoptionText -> {
                hide()
                listener.onFilterClick(3)
                binding.adoptionText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_select,0)
                dismiss()
            }
        }
    }
}

interface FilterBottomSheetListener {
    fun onFilterClick(postType: Int)
}
