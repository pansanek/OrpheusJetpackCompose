package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

interface PostRepository {
    fun addPostItem(postItem: PostItem)

    fun deletePostItem(postItem: PostItem)

    fun editPostItem(postItem: PostItem)

    fun getPostItem(postId: String): PostItem

    fun getPostsList(): List<PostItem>
}