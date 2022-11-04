package com.moralabs.pet.notification.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.observable.NotificationHandler
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.databinding.FragmentNotificationBinding
import com.moralabs.pet.databinding.ItemNotificationBinding
import com.moralabs.pet.mainPage.presentation.ui.CommentActivity
import com.moralabs.pet.message.presentation.ui.MessageDetailActivity
import com.moralabs.pet.notification.data.remote.dto.NotificationDto
import com.moralabs.pet.notification.presentation.viewmodel.NotificationViewModel
import com.moralabs.pet.offer.presentation.ui.OfferActivity
import com.moralabs.pet.profile.presentation.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding, List<NotificationDto>, NotificationViewModel>() {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun getLayoutId() = R.layout.fragment_notification
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<NotificationDto>> {
        val viewModel: NotificationViewModel by viewModels()
        return viewModel
    }

    private val notificationAdapter: BaseListAdapter<NotificationDto, ItemNotificationBinding> by lazy {
        BaseListAdapter(
            R.layout.item_notification, BR.item, onRowClick = { notification ->
                when (notification.type) {
                    NotificationType.NewComment.type -> {
                        val bundle = bundleOf(
                            NotificationActivity.CONTENT_ID to notification.contentId
                        )
                        val intent = Intent(context, CommentActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                    NotificationType.NewFollower.type -> {
                        val bundle = bundleOf(
                            NotificationActivity.OTHER_USER_ID to notification.contentId
                        )
                        val intent = Intent(context, ProfileActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                    NotificationType.NewPartnerOffer.type -> {
                        val bundle = bundleOf(
                            OfferActivity.OFFER_ID to notification.contentId
                        )
                        val intent = Intent(context, OfferActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                    NotificationType.NewAdoptionOffer.type -> {
                        val bundle = bundleOf(
                            OfferActivity.OFFER_ID to notification.contentId
                        )
                        val intent = Intent(context, OfferActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                    NotificationType.AcceptedOffer.type -> {
                        val bundle = bundleOf(
                            MessageDetailActivity.USER_ID to notification.contentId
                        )
                        val intent = Intent(context, MessageDetailActivity::class.java)
                        intent.putExtras(bundle)
                        context?.startActivity(intent)
                    }
                    NotificationType.DeclinedOffer.type -> {

                    }
                    NotificationType.LikePost.type -> {

                    }
                    NotificationType.LikeComment.type -> {

                    }
                }
            },
            emptyString = getString(R.string.noNotification)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = notificationAdapter
        viewModel.notificationPet()
    }

    override fun stateSuccess(data: List<NotificationDto>) {
        super.stateSuccess(data)

        notificationAdapter.submitList(data)
        notificationHandler.clearNotification()
    }
}

enum class NotificationType(val type: Int) {
    NewComment(1),
    NewFollower(2),
    NewPartnerOffer(3),
    NewAdoptionOffer(4),
    LikePost(5),
    LikeComment(6),
    AcceptedOffer(7),
    DeclinedOffer(8)
}