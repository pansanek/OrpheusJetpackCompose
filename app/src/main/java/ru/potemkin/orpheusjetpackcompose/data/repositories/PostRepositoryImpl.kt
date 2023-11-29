package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

): PostRepository {

    private val postList= mutableListOf<PostItem>()

    private var autoIncrementId =0

    override fun addPostItem(postItem: PostItem) {
        if(postItem.id == PostItem.UNDEFINED_ID) {
            postItem.id = autoIncrementId++
        }
        postList.add(postItem)
    }

    override fun deletePostItem(postItem: PostItem) {
        postList.remove(postItem)
    }

    override fun editPostItem(postItem: PostItem) {
        val oldElement = getPostItem(postItem.id)
        postList.remove(oldElement)
        addPostItem(postItem)
    }

    override fun getPostItem(postId: Int): PostItem {
        return postList.find {
            it.id == postId
        } ?: throw java.lang.RuntimeException("Element with id $postId not found")
    }

    override fun getPostsList(): List<PostItem> {
        return postList.toList()
    }
}