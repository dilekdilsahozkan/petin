package com.moralabs.pet.mainPage.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.FragmentPostReportBottomSheetBinding
import com.moralabs.pet.databinding.FragmentPostSettingBottomSheetBinding

class PostReportBottomSheetFragment(
    val listener: PostReportBottomSheetListener?,
    val postId: String?
    ) : BottomSheetDialogFragment(), View.OnClickListener {

    lateinit var binding: FragmentPostReportBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_post_report_bottom_sheet, null, false
        )

        binding.inappropriateText.setOnClickListener(this)
        binding.spamText.setOnClickListener(this)
        binding.fraudText.setOnClickListener(this)
        binding.nudityOrSexualityText.setOnClickListener(this)
        binding.otherText.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.otherText -> {
                dismiss()
                listener?.onReportClick(0)
            }
            R.id.inappropriateText -> {
                dismiss()
                listener?.onReportClick(1)
            }
            R.id.spamText -> {
                dismiss()
                listener?.onReportClick(2)
            }
            R.id.fraudText -> {
                dismiss()
                listener?.onReportClick(3)
            }
            R.id.nudityOrSexualityText -> {
                dismiss()
                listener?.onReportClick(4)
            }
        }
    }
}

interface PostReportBottomSheetListener {
    fun onReportClick(reportType: Int?)
}

internal enum class ReportTextType(val type: Int) {
    Other(0),
    Inappropriate(1),
    Spam(2),
    Fraud(3),
    NudityOrSexuality(4)
}