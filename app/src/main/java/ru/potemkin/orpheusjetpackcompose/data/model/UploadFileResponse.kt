package ru.potemkin.orpheusjetpackcompose.data.model

data class UploadFileResponse(
    val bucket_name: String,
    val file_name: String,
    val url: String
)