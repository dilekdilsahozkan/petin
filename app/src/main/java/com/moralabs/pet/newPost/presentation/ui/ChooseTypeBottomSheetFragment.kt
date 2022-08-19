package com.moralabs.pet.newPost.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.databinding.FragmentNewPostBottomSheetBinding

class ChooseTypeBottomSheetFragment(private val listener: ChooseTypeBottomSheetListener?)
    : BottomSheetDialogFragment(),View.OnClickListener {

    lateinit var binding: FragmentNewPostBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentNewPostBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postText.setOnClickListener(this)
        binding.qnaText.setOnClickListener(this)
        binding.findPartnerText.setOnClickListener(this)
        binding.adoptionText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener?.onItemClick(v?.id)
        dismiss()
    }
}
interface ChooseTypeBottomSheetListener {
    fun onItemClick(id:Int?)
}