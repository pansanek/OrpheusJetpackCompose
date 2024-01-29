package ru.potemkin.orpheusjetpackcompose.domain.entities

data class AdministratorItem(
    val id: String,
    val user: UserItem,
    val locationId: String
)
