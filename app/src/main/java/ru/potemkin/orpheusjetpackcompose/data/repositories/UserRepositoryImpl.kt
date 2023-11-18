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
    init{
        val users = listOf(
            UserItem(
                1,
                "Лиза",
                R.drawable.sample1
            ),
            UserItem(
                2,
                "Антон",
                R.drawable.sample2
            ),
            UserItem(
                3,
                "Тамби",
                R.drawable.sample3
            ),
            UserItem(
                4,
                "Саша",
                R.drawable.sample4
            ),
            UserItem(
                5,
                "Макар",
                R.drawable.sample5
            ),
            UserItem(
                6,
                "Дима",
                R.drawable.sample6
            ),
            UserItem(
                7,
                "Арс",
                R.drawable.sample7
            ),
            UserItem(
                8,
                "Сережа",
                R.drawable.sample8
            ))
        for (user in users){
            addUserItem(user)
        }
    }
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