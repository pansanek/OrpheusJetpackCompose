package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem

interface UserRepository {

    fun addUserItem(userItem: UserItem)

    fun deleteUserItem(userItem: UserItem)

    fun editUserItem(userItem: UserItem)

    fun getUserItem(userId: String): UserItem

    fun getMusicianItem(userItem: UserItem):MusicianItem

    fun getUsersList(): List<UserItem>
    fun getMusiciansList(): List<MusicianItem>
    fun addMusicianItem(musicianItem: MusicianItem)
    fun editMusicianItem(musicianItem: MusicianItem)
    fun getOtherUser(userId: String): UserItem
    fun getMyUser(): UserItem

    fun setMyUser(userItem: UserItem)
}



