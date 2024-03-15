package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

) : NotificationRepository {




    private val _notificationItems = mutableListOf<NotificationItem>()
    private val notificationItems: List<NotificationItem>
        get() = _notificationItems.toList()

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