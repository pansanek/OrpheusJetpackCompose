package ru.potemkin.orpheusjetpackcompose.domain.entities


data class LocationItem(
    var id: String,
    val adminId: String,
    var name: String,
    var address: String,
    var about: String,
    var profilePicture: PhotoUrlItem
)
