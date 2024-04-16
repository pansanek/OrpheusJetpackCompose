package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem

interface UserRepository {

    suspend fun addUserItem(userItem: UserItem)

    suspend fun deleteUserItem(userItem: UserItem)

    suspend fun editUserItem(userItem: UserItem)

    //fun getUserItem(userId: String): UserItem

    //suspend fun getMusicianItem(userItem: UserItem):MusicianItem

    fun getUsersList(): StateFlow<List<UserItem>>
    fun getMusiciansList(): StateFlow<List<MusicianItem>>
    suspend fun addMusicianItem(musicianItem: MusicianItem)
    suspend fun editMusicianItem(musicianItem: MusicianItem)
    //suspend fun getOtherUser(userId: String): UserItem
    fun getMyUser(): UserItem
    fun setMyUser(userItem: UserItem)
}



