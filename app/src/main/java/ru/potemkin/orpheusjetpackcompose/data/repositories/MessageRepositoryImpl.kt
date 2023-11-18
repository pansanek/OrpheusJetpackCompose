package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(

):MessageRepository {
    private val messageList= mutableListOf<MessageItem>()

    private var autoIncrementId =0
    init {
        val messages= listOf(
            MessageItem(
                1,
                "Привет! Как ты?",
                "12:15 PM",
                true
            ),
            MessageItem(
                2,
                "Хочешь встретиться?",
                "12:17 PM",
                true
            ),
            MessageItem(
                3,
                "Да!",
                "12:19 PM",
                false
            ),
            MessageItem(
                4,
                "Куда пойдем?",
                "12:20 PM",
                false
            ),
            MessageItem(
                5,
                "Как насчет бара?)",
                "12:21 PM",
                true
            ),
            MessageItem(
                6,
                "Опа, а давай",
                "12:15 PM",
                false
            ),
            MessageItem(
                7,
                "Только в какой?",
                "12:17 PM",
                false
            ),
            MessageItem(
                8,
                "В один на Маяковской",
                "12:19 PM",
                true
            ),
            MessageItem(
                9,
                "Очень хорошее место",
                "12:20 PM",
                true
            ),
            MessageItem(
                10,
                "Хорошо",
                "12:21 PM",
                false
            )

        )
        for (message in messages){
           addMessageItem(message)
        }
    }
    override fun addMessageItem(messageItem: MessageItem) {
        if(messageItem.id == MessageItem.UNDEFINED_ID) {
            messageItem.id = autoIncrementId++
        }
        messageList.add(messageItem)
    }

    override fun deleteMessageItem(messageItem: MessageItem) {
        messageList.remove(messageItem)
    }

    override fun editMessageItem(messageItem: MessageItem) {
        val oldElement = getMessageItem(messageItem.id)
        messageList.remove(oldElement)
        addMessageItem(messageItem)
    }

    override fun getMessageItem(messageId: Int): MessageItem {
        return messageList.find {
            it.id == messageId
        } ?: throw java.lang.RuntimeException("Element with id $messageId not found")
    }

    override fun getMessagesList(): List<MessageItem> {
        return messageList.toList()
    }
}