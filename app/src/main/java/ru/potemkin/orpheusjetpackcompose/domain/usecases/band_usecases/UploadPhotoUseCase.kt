package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(private val bandRepository: BandRepository) {
    suspend operator fun invoke(file: File, mimeType:String):String{
        return bandRepository.uploadPhoto(file, mimeType)
    }
}