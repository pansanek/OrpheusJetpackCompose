package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(file: File, mimeType:String):String{
        return locationRepository.uploadPhoto(file, mimeType)
    }
}