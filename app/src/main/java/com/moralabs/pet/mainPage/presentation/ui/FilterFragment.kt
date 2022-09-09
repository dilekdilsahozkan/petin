package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.FragmentFilterBinding

class FilterBottomSheetFragment(
    var listener: FilterBottomSheetListener,
) : BottomSheetDialogFragment(),
    View.OnClickListener {

    lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_filter, null, false
        )

        binding.allPostText.setOnClickListener(this)
        binding.postText.setOnClickListener(this)
        binding.qnaText.setOnClickListener(this)
        binding.findPartnerText.setOnClickListener(this)
        binding.adoptionText.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}

interface FilterBottomSheetListener {
    fun onItemClick(type: Int)
}