package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

interface PostRepository {
    fun addPostItem(postItem: PostItem)


    fun editPostItem(postItem: PostItem)

    fun getPostItem(postId: String): PostItem

    fun getPostsList(): List<PostItem>
    fun getComments(postItem: PostItem): List<CommentItem>
    suspend fun loadNextData()
}