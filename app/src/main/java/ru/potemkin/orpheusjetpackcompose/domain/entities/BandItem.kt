package ru.potemkin.orpheusjetpackcompose.domain.entities

data class BandItem(
    var id: String,
    val name: String,
    val members: List<String>,
    val genre: String,
    val photo: PhotoUrlItem
)
