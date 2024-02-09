package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    val creator_id: String,
    val caption: String,
    val creator_type: String,
)
