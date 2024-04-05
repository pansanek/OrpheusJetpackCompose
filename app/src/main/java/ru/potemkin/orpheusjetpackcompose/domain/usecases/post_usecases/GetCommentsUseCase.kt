package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val postRepository: PostRepository) {

    operator fun invoke(postId:String): List<CommentItem> {
        return postRepository.getComments(postId)
    }
}