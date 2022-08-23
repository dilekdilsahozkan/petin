package com.moralabs.pet.notification.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNotificationBinding
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationDto, NotificationViewModel>() {

    override fun getLayoutId() = R.layout.fragment_notification
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<NotificationDto> {
        val viewModel: NotificationViewModel by viewModels()
        return viewModel
    }
}