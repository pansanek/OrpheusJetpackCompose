package ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.NotificationRepository
import javax.inject.Inject

class AddNotificationUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    fun addNotificationItem(notificationItem: NotificationItem){
        notificationRepository.addNotificationItem(notificationItem);
    }
}