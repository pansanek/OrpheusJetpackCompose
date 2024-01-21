package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import android.util.Log
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

): PostRepository {

    private val _postList = mutableListOf<PostItem>()
    val postList: List<PostItem>
        get() = _postList.toList()


    override fun addPostItem(postItem: PostItem) {
        _postList.add(postItem)
    }

    override fun deletePostItem(postItem: PostItem) {
        _postList.remove(postItem)
    }

    override fun editPostItem(postItem: PostItem) {
        val oldElement = getPostItem(postItem.id)
        _postList.remove(oldElement)
        addPostItem(postItem)
    }

    override fun getPostItem(postId: String): PostItem {
        return _postList.find {
            it.id == postId
        } ?: throw java.lang.RuntimeException("Element with id $postId not found")
    }

    override fun getPostsList(): List<PostItem> {
        Log.d("POSTS","getPostList: ${_postList.toString()}")

        return _postList.toList()
    }


}