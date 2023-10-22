package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface UserRepository {

    fun addUserItem(userItem: UserItem)

    fun deleteUserItem(userItem: UserItem)

    fun editUserItem(userItem: UserItem)

    fun getUserItem(userId: Int): UserItem

    fun getUsersList(): List<UserItem>
}