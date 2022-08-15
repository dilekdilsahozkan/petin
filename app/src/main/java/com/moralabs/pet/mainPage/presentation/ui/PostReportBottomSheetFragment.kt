package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R

class PostReportBottomSheetFragment(private val listener: PostReportBottomSheetListener?)
    : BottomSheetDialogFragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_report_bottom_sheet, container, false)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

interface PostReportBottomSheetListener {
    fun onItemClick(id:Int?)
}