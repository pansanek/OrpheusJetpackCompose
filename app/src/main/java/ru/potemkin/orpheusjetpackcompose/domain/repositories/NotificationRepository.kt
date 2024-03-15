package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface NotificationRepository {
    fun getNotifications(toUser: UserItem): List<NotificationItem>
}