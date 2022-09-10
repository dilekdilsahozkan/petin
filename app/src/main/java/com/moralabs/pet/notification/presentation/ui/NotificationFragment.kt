package com.moralabs.pet.notification.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNotificationBinding
import com.moralabs.pet.databinding.ItemNotificationBinding
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, List<NotificationDto>, NotificationViewModel>() {

    override fun getLayoutId() = R.layout.fragment_notification
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<NotificationDto>> {
        val viewModel: NotificationViewModel by viewModels()
        return viewModel
    }

    private val notificationAdapter: BaseListAdapter<NotificationDto, ItemNotificationBinding> by lazy {
        BaseListAdapter(R.layout.item_notification, BR.item, onRowClick = {

        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = notificationAdapter
        viewModel.notificationPet()
    }

     override fun stateSuccess(data: List<NotificationDto>) {
        super.stateSuccess(data)

        notificationAdapter.submitList(data)
    }
}