package ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.NotificationRepository
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    fun getNotificationList(toUser: UserItem): List<NotificationItem>{
        return notificationRepository.getNotifications(toUser)
    }
}