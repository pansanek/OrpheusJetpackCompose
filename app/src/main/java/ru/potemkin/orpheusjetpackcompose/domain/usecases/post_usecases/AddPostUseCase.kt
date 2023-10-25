package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class AddPostUseCase(private val postRepository: PostRepository) {
    fun addPostItem(postItem: PostItem){
        postRepository.addPostItem(postItem);
    }
}