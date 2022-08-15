package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.NewPostBottomSheetDialogBinding

class NewPostBottomSheetFragment(private val listener: NewPostBottomSheetListener?)
    : BottomSheetDialogFragment(),View.OnClickListener {

    lateinit var binding: NewPostBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = NewPostBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postButton.setOnClickListener(this)
        binding.qnaButton.setOnClickListener(this)
        binding.findPartnerButton.setOnClickListener(this)
        binding.adoptionButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener?.onItemClick(v?.id)
        dismiss()
    }
}
interface NewPostBottomSheetListener {
    fun onItemClick(id:Int?)
}