package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.AttachmentDto
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.CommentsDto
import ru.potemkin.orpheusjetpackcompose.data.model.LikesDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PostMapper {
    fun mapPosts(listPostDto: List<PostDto>):List<PostItem>{
        Log.d("USERS",listPostDto.toString())
        val result = mutableListOf<PostItem>()

        for (postDto in listPostDto) {
            with(postDto) {
                val post = PostItem(
                    id = id,
                    userId = userId,
                    text = text,
                    date = mapTimestampToDate(date),
                    likes = likes.count,
                    comments = mapComments(comments),
                    attachments = mapAttachments(attachments)
                )
                result.add(post)
            }
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }

    private fun mapComments(comments:CommentsDto):List<CommentItem>{
        val result = mutableListOf<CommentItem>()

        for (commentDto in comments.comments) {
            with(commentDto) {
                val comment = CommentItem(
                    id = id,
                    authorId=authorId,
                    text= text,
                    date= mapTimestampToDate(date)
                )
                result.add(comment)
            }
        }
        return result
    }

    private fun mapAttachments(attachments:List<AttachmentDto>?):List<String>{
        val result = mutableListOf<String>()

        if (attachments != null) {
            for (attDto in attachments) {
                with(attDto) {
                    result.add(photo?.photoUrls.toString())
                }
            }
        }
        return result
    }
}