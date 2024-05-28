package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(file: File, mimeType:String):String{
        return postRepository.uploadPhoto(file, mimeType)
    }
}