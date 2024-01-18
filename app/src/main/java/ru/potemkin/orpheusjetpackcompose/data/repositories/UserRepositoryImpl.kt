package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

): UserRepository {

    private val userList= mutableListOf<UserItem>()

    private var autoIncrementId =0
    override fun addUserItem(userItem: UserItem) {
        if(userItem.id == UserItem.UNDEFINED_ID) {
            userItem.id = autoIncrementId++
        }
        userList.add(userItem)
    }

    override fun deleteUserItem(userItem: UserItem) {
        userList.remove(userItem)
    }

    override fun editUserItem(userItem: UserItem) {
        val oldElement = getUserItem(userItem.id)
        userList.remove(oldElement)
        addUserItem(userItem)
    }

    override fun getUserItem(userId: Int): UserItem {
        return userList.find {
            it.id == userId
        } ?: throw java.lang.RuntimeException("Element with id $userId not found")
    }

    override fun getUsersList(): List<UserItem> {
        return userList.toList()
    }
}