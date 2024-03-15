package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    fun getUserPosts(userId: String): List<PostItem> {
        return postRepository.getUserPosts(userId)
    }
}