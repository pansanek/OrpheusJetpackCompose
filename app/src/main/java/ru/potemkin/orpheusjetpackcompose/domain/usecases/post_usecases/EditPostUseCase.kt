package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class EditPostUseCase(private val psotRepository: PostRepository) {
    fun editPostItem(psotItem: PostItem){
        psotRepository.editPostItem(psotItem)
    }
}