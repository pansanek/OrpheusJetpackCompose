package ru.potemkin.orpheusjetpackcompose.domain.entities

data class MessageItem(
    var id: Int = UNDEFINED_ID,
    val message: String,
    val time: String,
    val direction: Boolean
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}


