package ru.potemkin.orpheusjetpackcompose.domain.entities


data class LocationItem(
    var id: String = UNDEFINED_ID,
    val adminId: String,
    var name: String,
    var address: String,
    var about: String,
    var profilePicture: PhotoUrlItem
){
    companion object {
        const val UNDEFINED_ID = "0"
    }
}
