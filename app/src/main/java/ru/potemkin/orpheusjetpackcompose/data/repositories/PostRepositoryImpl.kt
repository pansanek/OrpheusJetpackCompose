package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl@Inject constructor(
    application: Application
): PostRepository {

    private val postList= mutableListOf<PostItem>()

    private var autoIncrementId =0
    init{
        val posts = listOf(
            PostItem(
                id = 1,
                userId = 101,
                username = "user1",
                caption = "This is my first post",
                picture = R.drawable.sample2,
                timestamp = System.currentTimeMillis(),
                likes = 10,
                comments = listOf(
                    CommentItem(1, 201, "commenter1", "Great post!", System.currentTimeMillis()),
                    CommentItem(2, 202, "commenter2", "Nice pic!", System.currentTimeMillis())
                ),
                tags= listOf("#tag1")
                ),
            PostItem(
                id = 2,
                userId = 102,
                username = "user2",
                caption = "Beautiful sunset",
                picture = R.drawable.sample6,
                timestamp = System.currentTimeMillis(),
                likes = 15,
                comments = listOf(
                    CommentItem(3, 203, "commenter3", "Stunning view!", System.currentTimeMillis())
                ),
                tags = listOf()
            ),
            PostItem(
                id = 3,
                userId = 103,
                username = "user3",
                caption = "Foodie adventure",
                picture = R.drawable.sample3,
                timestamp = System.currentTimeMillis(),
                likes = 20,
                comments = emptyList(),
                tags = listOf()
            ),
            PostItem(
                id = 4,
                userId = 104,
                username = "user4",
                caption = "My pet cat",
                picture = R.drawable.sample5,
                timestamp = System.currentTimeMillis(),
                likes = 5,
                comments = emptyList(),
                tags = listOf()
            ),
            PostItem(
                id = 5,
                userId = 105,
                username = "user5",
                caption = "Coding time",
                picture = R.drawable.sample8,
                timestamp = System.currentTimeMillis(),
                likes = 12,
                comments = listOf(
                    CommentItem(4, 204, "commenter4", "Keep it up!", System.currentTimeMillis())
                ),
                tags = listOf("#tag1","#tag2")
            )
        )

        for (post in posts) {
            addPostItem(post)
        }

    }
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