package com.moralabs.pet.core.data.repository.impl

import com.moralabs.pet.core.data.remote.api.MediaService
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.core.data.repository.MediaRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val service: MediaService) :
    MediaRepository, BaseRepository {
    override suspend fun uploadPhoto(type: Int, file: File) = service.uploadMedia(
        MultipartBody.Part
            .createFormData(
                "type",
                type.toString()
            ), MultipartBody.Part
            .createFormData(
                name = "media",
                filename = file.name,
                body = file.asRequestBody()
            )
    )
}