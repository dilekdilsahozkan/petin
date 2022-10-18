package com.moralabs.pet.newPost.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import java.io.File
import kotlinx.android.parcel.Parcelize

@Keep
data class NewPostDto (
    var media: List<MediaDto>? = null,
    val text : String? = null,
    val type: Int? = null,
    val locationId: String? = null,
    val petId: String? = null,
    var files: List<File>? = null
): BaseDto()

@Keep
@Parcelize
data class MediaDto(
    val id: String? = null,
    val url: String? = null
) : BaseDto(), Parcelable

enum class MediaType(val value: Int) {
    POST(1),
    USER(2),
    PET(3),
    MESSAGE(4)
}