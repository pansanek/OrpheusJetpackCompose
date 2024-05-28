package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import java.io.File

interface PostRepository {
    suspend fun addPostItem(postItem: PostItem)
    suspend fun changeLikeStatus(postItemId: String,userId: String)
    suspend fun deletePostItem(postItem: PostItem)
    suspend fun editPostItem(postItem: PostItem)

    suspend fun getPostItem(postId: String): PostItem

    fun getPostsList(): StateFlow<List<PostItem>>
    fun getComments(postItem: PostItem): StateFlow<List<CommentItem>>
    suspend fun addCommentItem(commentItem: CommentItem)
    suspend fun loadNextData()
    suspend fun uploadPhoto(file: File, mimeType: String):String

    //fun getUserPosts(userId: String): StateFlow<List<PostItem>>

    //fun getBandPosts(bandId: String): StateFlow<List<PostItem>>
    //fun getLocationPosts(locationId: String): StateFlow<List<PostItem>>

}

