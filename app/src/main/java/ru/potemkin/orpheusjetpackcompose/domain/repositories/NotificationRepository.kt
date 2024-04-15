package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface NotificationRepository {
    suspend fun addNotificationItem(notificationItem: NotificationItem)
    fun getNotifications(toUser: UserItem): StateFlow<List<NotificationItem>>
    fun getAllNotifications(): StateFlow<List<NotificationItem>>
}

