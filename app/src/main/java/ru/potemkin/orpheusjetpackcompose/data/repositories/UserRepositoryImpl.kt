package ru.potemkin.orpheusjetpackcompose.data.repositories

import androidx.compose.runtime.mutableStateOf
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
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

    private val _myUser= UserItem(
        "1",
        "pansanek",
        "Sasha",
        "12341234",
        "email@gmail.com",
        "Hehe",
        UserType.MUSICIAN,
        PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
        PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"),
        UserSettingsItem(true,true)
    )

    init {
        addMockData()
    }
    override fun addUserItem(userItem: UserItem) {
        _userItems.add(userItem)
    }

    override fun getMyUser():UserItem {
        return UserItem(
            _myUser.id,
            _myUser.login,
            _myUser.name,
            _myUser.password,
            _myUser.email,
            _myUser.about,
            _myUser.user_type,
            _myUser.profile_picture,
            _myUser.background_picture,
            _myUser.settings
        )
    }

    override fun setMyUser(userItem: UserItem) {
        _myUser.id = userItem.id
        _myUser.login = userItem.login
        _myUser.name = userItem.name
        _myUser.password = userItem.password
        _myUser.email = userItem.email
        _myUser.about = userItem.about
        _myUser.user_type = userItem.user_type
        _myUser.profile_picture = userItem.profile_picture
        _myUser.background_picture = userItem.background_picture
        _myUser.settings = userItem.settings
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

    override fun getOtherUser(userId: String): UserItem {
        TODO("Not yet implemented")
    }

    fun addMockData() {
        addUserItem( UserItem(
            "1",
            "pansanek",
            "Sasha",
            "12341234",
            "email@gmail.com",
            "Hehe",
            UserType.MUSICIAN,
            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"),
            UserSettingsItem(true,true)
        ))
        addUserItem(UserItem(
            "2",
            "noahbadomens",
            "Noah Sebastian",
            "12341234",
            "email@gmail.com",
            "Vocalist for Bad Omens",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
            ),
            PhotoUrlItem(
                "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            ),
            UserSettingsItem(true, true)
        ))
    }

}