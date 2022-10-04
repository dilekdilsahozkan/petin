package com.moralabs.pet.petProfile.presentation.model

import android.os.Parcelable
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.petProfile.data.remote.dto.AttributeDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttributeUiDto(
    val uiType: AttributeUiType,
    val attributeDto: AttributeDto,
    var isSelected: Boolean = false,
    var choiceId: String? = null,
    var choice: String? = null,
    var media: MediaDto? = null
) : BaseDto(), Parcelable

enum class AttributeUiType(val value: Int) {
    PHOTO(1),
    NAME(2),
    ATTRIBUTE(3),
    ATTRIBUTE_LIST(4)
}