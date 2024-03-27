package ru.potemkin.orpheusjetpackcompose.domain.entities

enum class NotificationType {
    LIKE, BAND_INVITE
}

data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val title: String,
    val contentDescription: String,
    val date: String,
    var fromUser: UserItem,
    var toUser: UserItem,
    var postItem: PostItem?,
    var bandItem: BandItem?
)

