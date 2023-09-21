package ru.potemkin.orpheusjetpackcompose.data

data class Chat(
    val id: Int,
    val message: String,
    val time: String,
    val direction: Boolean
)

val chatList= listOf(
    Chat(
        1,
        "Привет! Как ты?",
        "12:15 PM",
        true
    ),
    Chat(
        2,
        "Хочешь встретиться?",
        "12:17 PM",
        true
    ),
    Chat(
        3,
        "Да!",
        "12:19 PM",
        false
    ),
    Chat(
        4,
        "Куда пойдем?",
        "12:20 PM",
        false
    ),
    Chat(
        5,
        "Как насчет бара?)",
        "12:21 PM",
        true
    ),
    Chat(
        6,
        "Опа, а давай",
        "12:15 PM",
        false
    ),
    Chat(
        7,
        "Только в какой?",
        "12:17 PM",
        false
    ),
    Chat(
        8,
        "В один на Маяковской",
        "12:19 PM",
        true
    ),
    Chat(
        9,
        "Очень хорошее место",
        "12:20 PM",
        true
    ),
    Chat(
        10,
        "Хорошо",
        "12:21 PM",
        false
    )
)