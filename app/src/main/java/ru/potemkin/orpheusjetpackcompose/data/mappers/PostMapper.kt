package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem


class PostMapper {
    val userMapper = UsersMapper()
    val bandMapper = BandMapper()
    val locationMapper = LocationMapper()
    suspend fun mapPostList(postDtoList: List<PostDto>): List<PostItem> {
        val result = mutableListOf<PostItem>()
        for (postDto in postDtoList) {
            val postItem = mapPost(postDto)
            result.add(postItem)
        }
        return result
    }

    suspend fun mapPost(postDto: PostDto): PostItem {
        if(postDto.creatorType == "User"){
            val postItem = PostItem(
                id = postDto.postId,
                creatorName = mapCreatorToUser(postDto.creatorId).name,
                creatorPicture = mapCreatorToUser(postDto.creatorId).profile_picture,
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
        else if (postDto.creatorType == "Band") {
            val postItem = PostItem(
                id = postDto.postId,
                creatorName = mapCreatorToBand(postDto.creatorId).name,
                creatorPicture = mapCreatorToBand(postDto.creatorId).photo,
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
        else {
            val postItem = PostItem(
                id = postDto.postId,
                creatorName = mapCreatorToLocation(postDto.creatorId).name,
                creatorPicture = mapCreatorToLocation(postDto.creatorId).profilePicture,
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

    private suspend fun mapCreatorToUser(creatorId:String):UserItem{
        return userMapper.mapUser(ApiFactory.appUserApiService.getUserById(creatorId))

    }

    private suspend fun mapCreatorToBand(creatorId:String):BandItem{
        return bandMapper.mapBand(ApiFactory.appBandApiService.getBandById(creatorId))
    }

    private suspend fun mapCreatorToLocation(creatorId:String): LocationItem {
        return locationMapper.mapLocation(ApiFactory.appLocationApiService.getLocationById(creatorId))
    }

    private fun mapAttachmentList(photoUrlDtoList: List<PhotoUrlDto>): List<String> {
        val result = mutableListOf<String>()
        for (photoUrlDto in photoUrlDtoList) {
            result.add(photoUrlDto.url)
        }
        return result
    }
}
