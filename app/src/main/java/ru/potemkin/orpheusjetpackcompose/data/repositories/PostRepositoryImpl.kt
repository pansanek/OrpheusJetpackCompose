package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.network.PostApiService
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

): PostRepository {

    private val apiService = ApiFactory.appPostApiService
    private val mapper = PostMapper()

    private val _postItems = mutableListOf<PostItem>()
    private val postItems: List<PostItem>
        get() = _postItems.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<PostItem> {
        val startFrom = nextFrom

        if (startFrom == null && postItems.isNotEmpty()) return postItems

        val response = if (startFrom == null) {
            apiService.getAllPosts()
        } else {
            apiService.getAllPosts()
        }
        val posts = mapper.mapPostList(response)
        _postItems.addAll(posts)
        return postItems
    }

    override fun addPostItem(postItem: PostItem) {
        _postItems.add(postItem)
    }

    override fun getComments(postItem: PostItem): List<CommentItem> {
        return postItem.comments
    }

    override fun editPostItem(postItem: PostItem) {
        val oldElement = getPostItem(postItem.id)
        _postItems.remove(oldElement)
        addPostItem(postItem)
    }

    override fun getPostItem(postId: String): PostItem {
        return postItems.find {
            it.id == postId
        } ?: throw java.lang.RuntimeException("Element with id $postId not found")
    }


    override fun getPostsList(): List<PostItem> = _postItems

    override suspend fun loadNextData() {
        TODO("Not yet implemented")
    }


}