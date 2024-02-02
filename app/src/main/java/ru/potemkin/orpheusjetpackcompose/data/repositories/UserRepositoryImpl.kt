package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

): UserRepository {

    private val apiService = ApiFactory.appUserApiService
    private val mapper = UsersMapper()
    private val _userItems = mutableListOf<UserItem>()
    private val userItems: List<UserItem>
        get() = _userItems.toList()

    private var nextFrom: String? = null

    private val postApiService = ApiFactory.appPostApiService
    private val postMapper = PostMapper()

    private val _postItems = mutableListOf<PostItem>()
    private val postItems: List<PostItem>
        get() = _postItems.toList()

    private var postNextFrom: String? = null

    suspend fun loadPosts(userid:String): List<PostItem> {
        val startFrom = postNextFrom

        if (startFrom == null && postItems.isNotEmpty()) return postItems

        val response = if (startFrom == null) {
            postApiService.getCreatorsPosts(userid)
        } else {
            postApiService.getCreatorsPosts(userid)
        }
        val posts = postMapper.mapPostList(response)
        _postItems.addAll(posts)
        return postItems
    }
    override fun addUserItem(userItem: UserItem) {
        _userItems.add(userItem)
    }
    suspend fun loadUsers(): List<UserItem> {
        val startFrom = nextFrom

        if (startFrom == null && userItems.isNotEmpty()) return userItems

        val response = if (startFrom == null) {
            apiService.getAllUsers()
        } else {
            apiService.getAllUsers()
        }
        val posts = mapper.mapUsers(response)
        _userItems.addAll(posts)
        return userItems
    }
    fun getMyUser():UserItem {
        TODO()
//        return _userItems.find {
//            it.id ==
//        } ?: throw java.lang.RuntimeException("Element with id $userId not found")
    }
    override fun deleteUserItem(userItem: UserItem) {
        _userItems.remove(userItem)
    }

    override fun editUserItem(userItem: UserItem) {
        val oldElement = getUserItem(userItem.id)
        _userItems.remove(oldElement)
        addUserItem(userItem)
    }

    override fun getUserItem(userId: String): UserItem {
        return _userItems.find {
            it.id == userId
        } ?: throw java.lang.RuntimeException("Element with id $userId not found")
    }

    override fun getUsersList(): List<UserItem> {
        return _userItems.toList()
    }
}