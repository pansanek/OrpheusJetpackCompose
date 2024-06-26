package ru.potemkin.orpheusjetpackcompose.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
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
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

) : NotificationRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)


    private val _notificationItems = mutableListOf<NotificationItem>()
    private val notificationItems: List<NotificationItem>
        get() = _notificationItems.toList()

    private val refreshedListFlow = MutableSharedFlow<List<NotificationItem>>()
    private val loadedListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (notificationItems.isNotEmpty()) {
            emit(notificationItems)
        } else {
            // Если постов нет, добавляем моковые данные
            addMockData()
            emit(notificationItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val notifications: StateFlow<List<NotificationItem>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = notificationItems
        )

    override suspend fun addNotificationItem(notificationItem: NotificationItem) {
        _notificationItems.add(notificationItem)
        refreshedListFlow.emit(notificationItems)
    }

    override fun getAllNotifications(): StateFlow<List<NotificationItem>> = notifications

    fun getLocalNotificationList(): List<NotificationItem>  = _notificationItems

    suspend fun addMockData() {
        addNotificationItem(
            NotificationItem(
                id = "111",
                type = NotificationType.LIKE,
                bandItem = null,
                contentDescription = " оценил вашу запись от ",
                title = "Ваш пост оценили",
                fromUser = UserItem(
                    "14",
                    "dayseekerrory",
                    "Рори Родригес",
                    "12341234",
                    "4@gmail.com",
                    "Просто хороший парень",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "194",
                        "https://images.genius.com/4321352a6796b4d618f8324ccdc68181.1000x1000x1.jpg"
                    ),
                    PhotoUrlItem(
                        "1104",
                        "https://www.bringthenoiseuk.com/wp-content/uploads/Dayseeker-2022-Credit-Amber-Paredes.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                toUser = UserItem(
                    "15",
                    "jessiecash5",
                    "Джесси Кэш",
                    "12341234",
                    "5@gmail.com",
                    "Ghost Atlas / Erra",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "195",
                        "https://thenewfury.com/wp-content/uploads/2017/10/unnamed-1.jpg"
                    ),
                    PhotoUrlItem(
                        "1105",
                        "https://i.ytimg.com/vi/7VvYYohzaj0/maxresdefault.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                date = "06-03-24",
                postItem = PostItem(
                    id = "31",
                    creatorName = "pansanek",
                    creatorId = "11",
                    creatorPicture = PhotoUrlItem(
                        "191",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    creatorType = CreatorType.USER,
                    text = "Первый пост!",
                    date = "15/2/2024 10:01",
                    comments = mutableListOf<CommentItem>(
                        CommentItem(
                            "21",
                            "31",
                            UserItem(
                                "12",
                                "noahbadomens",
                                "Ной Себастьян",
                                "12341234",
                                "email@gmail.com",
                                "Вокалист",
                                UserType.MUSICIAN,
                                PhotoUrlItem(
                                    "192",
                                    "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                                ),
                                PhotoUrlItem(
                                    "1102",
                                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                                ),
                                UserSettingsItem(true, true)
                            ),
                            "Круто",
                            "15/2/2024 12:37"
                        )
                    ),
                    likes = listOf<String>(),
                    attachment = PhotoUrlItem(
                        "391",
                        "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                    ),
                    isLiked = false,
                    statistics = mutableListOf(
                        StatisticItem(StatisticType.LIKES, 0),
                        StatisticItem(StatisticType.COMMENTS, 1)
                    )
                )
            )
        )
        addNotificationItem(
            NotificationItem(
                id = "112",
                type = NotificationType.BAND_INVITE,
                bandItem = BandItem(
                    "73",
                    "The Plot In You",
                    listOf(
                        UserItem(
                            "19",
                            "landontewers",
                            "Landon Tewers",
                            "12341234",
                            "9@gmail.com",
                            "Много пою и кричу",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "199",
                                "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                            ),
                            PhotoUrlItem(
                                "1109",
                                "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                            ),
                            UserSettingsItem(true, true)
                        )
                    ),
                    "Металкор",
                    PhotoUrlItem(
                        "793",
                        "https://substreammagazine.com/wp-content/uploads/2018/01/Main1-2000x1333.jpg"
                    )
                ),
                contentDescription = " пригласил вас в группу ",
                title = "Вас пригласили в группу",
                fromUser = UserItem(
                    "19",
                    "landontewers",
                    "Лэндон Тьюерс",
                    "12341234",
                    "9@gmail.com",
                    "Много пою и кричу",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "199",
                        "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                    ),
                    PhotoUrlItem(
                        "1109",
                        "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                    ),
                    UserSettingsItem(true, true)
                ),
                toUser = UserItem(
                    "11",
                    "pansanek",
                    "Саша Потемкин",
                    "12341234",
                    "1@gmail.com",
                    "Просто барабанщик, гитарист, басист и так далее.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "191",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "1101",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                postItem = null,
                date = "06-03-24"
            )
        )
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }
}