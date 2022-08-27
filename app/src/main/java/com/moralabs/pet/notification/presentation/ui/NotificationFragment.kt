package com.moralabs.pet.notification.presentation.ui

import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentNotificationBinding
import com.moralabs.pet.databinding.ItemNotificationBinding
import com.moralabs.pet.notification.data.remote.dto.GroupNotification
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, GroupNotification, NotificationViewModel>() {

    override fun getLayoutId() = R.layout.fragment_notification
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<GroupNotification> {
        val viewModel: NotificationViewModel by viewModels()
        return viewModel
    }

   /* override fun stateSuccess(data: GroupNotification) {
        super.stateSuccess(data)
        notificationAdapter.submitList(data.notification)
    }*/

    private val notificationAdapter: BaseListAdapter<GroupNotification, ItemNotificationBinding> by lazy {
        BaseListAdapter(R.layout.item_notification, BR.item, onRowClick = {

        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

}