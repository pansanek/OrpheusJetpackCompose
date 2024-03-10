package ru.potemkin.orpheusjetpackcompose.presentation.post

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class PostCreationScreenState {

    object Initial : PostCreationScreenState()

    data class CreatorInfo(
        val creatorInfo: CreatorInfoItem
    ) : PostCreationScreenState()
}