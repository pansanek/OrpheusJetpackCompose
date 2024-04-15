package ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.NotificationRepository
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(toUser: UserItem): StateFlow<List<NotificationItem>> {
        return notificationRepository.getNotifications(toUser)
    }
}