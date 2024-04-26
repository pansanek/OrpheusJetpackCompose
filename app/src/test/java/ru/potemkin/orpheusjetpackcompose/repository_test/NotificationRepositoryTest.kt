package ru.potemkin.orpheusjetpackcompose.repository_test

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.NotificationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class NotificationRepositoryTest {

    private lateinit var notificationRepository: NotificationRepositoryImpl

    @Before
    fun setUp() {
        notificationRepository = NotificationRepositoryImpl()
    }

    @Test
    fun `test addNotificationItem`() = runBlocking {
        // Given
        val notificationItem = NotificationItem(
                id = "1",
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

        // When
        notificationRepository.addNotificationItem(notificationItem)

        // Then
        val notifications = notificationRepository.getLocalNotificationList()
        assertEquals(1, notifications.size)
        assertEquals(notificationItem, notifications.first())
    }

    @Test
    fun `test getAllNotifications`() = runBlocking {
        // Given
        val notificationItem1 = NotificationItem(
                id = "1",
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
        notificationRepository.addNotificationItem(notificationItem1)

        // When
        val notifications = notificationRepository.getLocalNotificationList()

        // Then
        assertEquals(1, notifications.size)
    }

    @Test
    fun `test getAllNotifications with empty list`() = runBlocking {
        // When
        val notifications = notificationRepository.getLocalNotificationList()

        // Then
        assertEquals(0, notifications.size)
    }


}
