package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(postItem: CommentItem){
        postRepository.addCommentItem(postItem);
    }
}