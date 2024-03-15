package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

) : NotificationRepository {

    private val _notificationItems = mutableListOf<NotificationItem>()
    private val notificationItems: List<NotificationItem>
        get() = _notificationItems.toList()

    init {
        addNotificationItem(NotificationItem(
            id= "1",
            type = NotificationType.LIKE,
            bandItem = null,
            contentDescription = " оценил вашу запись от ",
            title = "Ваш пост оценили",
            fromUser = UserItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "noahbadomens",
                "Noah Sebastian",
                "12341234",
                "email@gmail.com",
                "Vocalist for Bad Omens",
                UserType.MUSICIAN,
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"),
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"),
                UserSettingsItem(true,true)
            ),
            toUser = UserItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "pansanek",
                "Sasha",
                "12341234",
                "email@gmail.com",
                "Hehe",
                UserType.MUSICIAN,
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                UserSettingsItem(true,true)
            ),
            date = "06-03-24",
            postItem = PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "pansanek" ,
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                creatorType = CreatorType.USER,
                text = "Test post",
                date = "01-07-21",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "5600434b-7627-45fc-af8b-9f92e838c2e7",
                        "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                        UserItem(
                            "51bdc118-e76b-4372-8678-6822658cefed",
                            "pansanek",
                            "Sasha",
                            "12341234",
                            "email@gmail.com",
                            "Hehe",
                            UserType.MUSICIAN,
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            UserSettingsItem(true,true)
                        ),
                        "Nice",
                        "01-07-21"
                    )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,1)
                ),
            )
        ))
        addNotificationItem(NotificationItem(
            id= "1",
            type = NotificationType.BAND_INVITE,
            bandItem = BandItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "Bad Omens",
                listOf(
                    UserItem(
                        "51bdc118-e76b-4372-8678-6822658cefed",
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
                    ),
                    UserItem(
                        "51bdc118-e76b-4372-8678-6822658cefed",
                        "pansanek",
                        "Sasha",
                        "12341234",
                        "email@gmail.com",
                        "Hehe",
                        UserType.MUSICIAN,
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                            "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                        ),
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                            "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                        ),
                        UserSettingsItem(true, true)
                    )
                ),
                "Metalcore",
                PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                )
            ),
            contentDescription = " пригласил вас в группу ",
            title = "Вас пригласили в группу",
            fromUser = UserItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "noahbadomens",
                "Noah Sebastian",
                "12341234",
                "email@gmail.com",
                "Vocalist for Bad Omens",
                UserType.MUSICIAN,
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"),
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"),
                UserSettingsItem(true,true)
            ),
            toUser = UserItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "pansanek",
                "Sasha",
                "12341234",
                "email@gmail.com",
                "Hehe",
                UserType.MUSICIAN,
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                UserSettingsItem(true,true)
            ),
            postItem = null,
            date = "06-03-24"
        ))
    }
    override fun addNotificationItem(notificationItem: NotificationItem) {
        _notificationItems.add(notificationItem)
    }


    override fun getNotifications(toUser: UserItem): List<NotificationItem> {
        val userNotifications = mutableListOf<NotificationItem>()
        for (notification in _notificationItems){
            if(notification.toUser == toUser) userNotifications.add(notification)
        }
        return userNotifications
    }
}