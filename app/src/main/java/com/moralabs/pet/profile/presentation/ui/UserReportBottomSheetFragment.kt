package com.moralabs.pet.profile.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moralabs.pet.R
import com.moralabs.pet.databinding.FragmentUserReportBottomSheetBinding

class UserReportBottomSheetFragment(
    val listener: UserReportBottomSheetListener?,
    val userId: String?
) : BottomSheetDialogFragment(), View.OnClickListener {

    lateinit var binding: FragmentUserReportBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_user_report_bottom_sheet, null, false
        )

        binding.inappropriate.setOnClickListener(this)
        binding.identityTheft.setOnClickListener(this)
        binding.underageUser.setOnClickListener(this)
        binding.spamText.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.inappropriate -> {
                dismiss()
                listener?.onSelectReportClick(userId, 1)
            }
            R.id.identityTheft -> {
                dismiss()
                listener?.onSelectReportClick(userId, 2)
            }
            R.id.underageUser -> {
                dismiss()
                listener?.onSelectReportClick(userId, 3)
            }
            R.id.spamText -> {
                dismiss()
                listener?.onSelectReportClick(userId, 4)
            }
        }
    }
}

interface UserReportBottomSheetListener {
    fun onSelectReportClick(userId: String?, reportType: Int?)
}