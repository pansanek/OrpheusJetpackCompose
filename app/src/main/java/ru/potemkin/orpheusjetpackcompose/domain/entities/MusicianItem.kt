package ru.potemkin.orpheusjetpackcompose.domain.entities

data class MusicianItem(
    var id: String,
    val user: UserItem,
    var genre: String,
    var instrument: String
)
