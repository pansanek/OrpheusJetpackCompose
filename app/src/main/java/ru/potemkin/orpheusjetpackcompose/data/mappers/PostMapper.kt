package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PostMapper {
    val userMapper = UsersMapper()
    fun mapPostList(postDtoList: List<PostDto>): List<PostItem> {
        val result = mutableListOf<PostItem>()
        for (postDto in postDtoList) {
            val postItem = mapPost(postDto)
            result.add(postItem)
        }
        return result
    }

    fun mapPost(postDto: PostDto): PostItem {
        val postItem = PostItem(
            id = postDto.postId,
            creatorId = postDto.creatorId,
            text = postDto.caption,
            date = postDto.timestamp,
            likes = postDto.likes.size,
            comments = mapCommentList(postDto.comments),
            attachments = mapAttachmentList(postDto.attachment),
            creatorType = postDto.creatorType,
            isLiked = false,
            statistics = listOf(
                StatisticItem(type = StatisticType.LIKES, postDto.likes.size),
                StatisticItem(type = StatisticType.COMMENTS, postDto.comments.size)
            ),
        )

        return postItem
    }

    private fun mapCommentList(commentDtoList: List<CommentDto>): List<CommentItem> {
        val result = mutableListOf<CommentItem>()
        for (commentDto in commentDtoList) {
            val commentItem = CommentItem(
                id = commentDto.id,
                user = userMapper.mapUser(commentDto.user),
                post_id = commentDto.post_id,
                text = commentDto.text,
                timestamp = commentDto.timestamp
            )
            result.add(commentItem)
        }
        return result
    }

    private fun mapAttachmentList(photoUrlDtoList: List<PhotoUrlDto>): List<String> {
        val result = mutableListOf<String>()
        for (photoUrlDto in photoUrlDtoList) {
            result.add(photoUrlDto.url)
        }
        return result
    }
}
