package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class GetPostListUseCase (private val postRepository: PostRepository) {
    fun getPostList(): List<PostItem>{
        return postRepository.getPostsList()
    }
}