package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments.CommentsScreenState

class GetCommentsUseCase (
    private val repository: PostRepository
) {

    operator fun invoke(feedPost: PostItem): List<CommentItem> {
        return repository.getComments(feedPost)
    }
}