package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem

interface UserRepository {

    fun addUserItem(userItem: UserItem)

    fun deleteUserItem(userItem: UserItem)

    fun editUserItem(userItem: UserItem)

    fun getUserItem(userId: String): UserItem

    fun getUsersList(): List<UserItem>
    fun getOtherUser(userId: String): UserItem
    fun getMyUser(): UserItem
}