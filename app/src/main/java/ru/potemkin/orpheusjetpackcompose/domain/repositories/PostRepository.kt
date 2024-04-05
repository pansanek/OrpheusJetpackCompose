package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

interface PostRepository {
    fun addPostItem(postItem: PostItem)
    fun changeLikeStatus(postItemId: String)
    fun deletePostItem(postItem: PostItem)
    fun editPostItem(postItem: PostItem)

    fun getPostItem(postId: String): PostItem

    fun getPostsList(): List<PostItem>
    fun getComments(postId: String): List<CommentItem>
    fun addCommentItem(commentItem: CommentItem)
    suspend fun loadNextData()

    fun getUserPosts(userId: String): List<PostItem>

    fun getBandPosts(bandId: String): List<PostItem>
    fun getLocationPosts(locationId: String): List<PostItem>

}

