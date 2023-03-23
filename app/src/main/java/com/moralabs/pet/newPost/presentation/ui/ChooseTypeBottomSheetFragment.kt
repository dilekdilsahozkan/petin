package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.ChooseTypeBottomSheetBinding

class ChooseTypeBottomSheetFragment(
    var listener: ChooseTypeBottomSheetListener,
) : BottomSheetDialogFragment(),
    View.OnClickListener {

    lateinit var binding: ChooseTypeBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.choose_type_bottom_sheet, null, false
        )

        binding.postText.setOnClickListener(this)
        binding.qnaText.setOnClickListener(this)
        binding.findPartnerText.setOnClickListener(this)
        binding.adoptionText.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.postText -> {
                dismiss()
                listener.onItemClick(0)
            }
            R.id.qnaText -> {
                dismiss()
                listener.onItemClick(1)
            }
            R.id.findPartnerText -> {
                dismiss()
                listener.onItemClick(2)
            }
            R.id.adoptionText -> {
                dismiss()
                listener.onItemClick(3)
            }
        }
    }
}

interface ChooseTypeBottomSheetListener {
    fun onItemClick(type: Int)
}

internal enum class TabTextType(val type: Int) {
    POST_TYPE(0),
    QAN_TYPE(1),
    FIND_PARTNER_TYPE(2),
    ADOPTION_TYPE(3),
    ALL_POST(4)
}