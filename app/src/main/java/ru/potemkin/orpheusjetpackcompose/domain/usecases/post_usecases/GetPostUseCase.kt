package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class GetPostUseCase (private val postRepository: PostRepository) {
    fun getPostItem(postItemId: Int): PostItem {
        return postRepository.getPostItem(postItemId)
    }
}