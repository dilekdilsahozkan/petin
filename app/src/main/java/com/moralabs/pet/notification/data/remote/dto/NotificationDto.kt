package com.moralabs.pet.notification.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

class NotificationDto (
    val text: String? = null,
    val type: Int? = null,
    val contentId: String? = null,
    val dateTime: String? = null,
    val pageIndex: Int? = null
): BaseDto()