package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetBandPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    fun getBandPosts(bandId: String): List<PostItem> {
        return postRepository.getBandPosts(bandId)
    }
}