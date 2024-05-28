package ru.potemkin.orpheusjetpackcompose.data.model

data class UploadFileResponse(
    val fileName: String,
    val fileUrl: String
    // Добавьте другие поля, если они есть в ответе
)