package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class DeletePostUseCase (private val postRepository: PostRepository){
    fun deletePostItem(PostItem: PostItem){
        postRepository.deletePostItem(PostItem)
    }
}