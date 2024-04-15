package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

interface PostRepository {
    suspend fun addPostItem(postItem: PostItem)
    suspend fun changeLikeStatus(postItemId: String)
    fun deletePostItem(postItem: PostItem)
    suspend fun editPostItem(postItem: PostItem)

    fun getPostItem(postId: String): PostItem

    fun getPostsList(): StateFlow<List<PostItem>>
    fun getComments(postId: String): List<CommentItem>
    suspend fun addCommentItem(commentItem: CommentItem)
    suspend fun loadNextData()

    fun getUserPosts(userId: String): List<PostItem>

    fun getBandPosts(bandId: String): List<PostItem>
    fun getLocationPosts(locationId: String): List<PostItem>

}

