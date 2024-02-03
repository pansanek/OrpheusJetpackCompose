package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository

class GetCommentsUseCase (
    private val repository: PostRepository
) {

    operator fun invoke(feedPost: PostItem): List<CommentItem> {
        return repository.getComments(feedPost)
    }
}