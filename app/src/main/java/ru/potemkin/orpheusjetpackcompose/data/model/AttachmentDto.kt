package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class AttachmentDto(
    @SerializedName("attachmentId") val id: List<PhotoUrlDto>?,
    @SerializedName("photo") val photo: List<PhotoUrlDto>?
)