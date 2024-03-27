package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetLocationPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(locationId: String): List<PostItem> {
        return postRepository.getLocationPosts(locationId)
    }
}