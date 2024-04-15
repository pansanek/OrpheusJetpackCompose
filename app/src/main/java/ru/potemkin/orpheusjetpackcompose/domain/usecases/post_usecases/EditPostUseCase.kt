package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class EditPostUseCase @Inject constructor(private val psotRepository: PostRepository) {
    suspend operator fun invoke(psotItem: PostItem){
        psotRepository.editPostItem(psotItem)
    }
}