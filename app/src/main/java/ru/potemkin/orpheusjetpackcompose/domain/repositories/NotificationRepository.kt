package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface NotificationRepository {
    fun addNotificationItem(notificationItem: NotificationItem)
    fun getNotifications(toUser: UserItem): List<NotificationItem>
    fun getAllNotifications(): List<NotificationItem>
}

