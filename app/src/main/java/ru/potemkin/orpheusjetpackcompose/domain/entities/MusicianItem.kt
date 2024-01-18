package ru.potemkin.orpheusjetpackcompose.domain.entities

data class MusicianItem(
    var id: String,
    val userId: String,
    var genre: String,
    var instrument: String
)
