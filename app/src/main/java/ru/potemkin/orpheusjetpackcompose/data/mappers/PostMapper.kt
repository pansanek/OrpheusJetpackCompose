package ru.potemkin.orpheusjetpackcompose.data.mappers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreatePostRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem


class PostMapper {
    val userMapper = UsersMapper()
    val bandMapper = BandMapper()
    val locationMapper = LocationMapper()
    val userRepositoryImpl = UserRepositoryImpl()
    val myUser = userRepositoryImpl.getMyUser()
    suspend fun mapPostList(postDtoList: List<PostDto>): List<PostItem> {
        val result = mutableListOf<PostItem>()
        for (postDto in postDtoList) {
            result.add(mapPost(postDto))
        }
        return result
    }

    fun mapPostToRequest(creatorId: String,creatorType:String,caption:String): CreatePostRequest {
        return CreatePostRequest(
            creator_id = creatorId,
            creator_type = creatorType,
            caption = caption,
        )
    }
    suspend fun mapPost(postDto: PostDto): PostItem {
        if(postDto.creatorType == "User"){
            val postItem = PostItem(
                id = postDto.postId,
                creatorId = postDto.creatorId,
                creatorName = mapCreatorToUser(postDto.creatorId).name,
                creatorPicture = mapCreatorToUser(postDto.creatorId).profile_picture,
                text = postDto.text,
                date = postDto.date,
                comments = mapCommentList(postDto.comments),
                attachment = mapAttachment(postDto.attachment),
                creatorType = CreatorType.valueOf(postDto.creatorType),
                likes = postDto.likes,
                isLiked = checkIfPostIsLiked(postDto.likes),
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
                creatorId = postDto.creatorId,
                creatorName = mapCreatorToBand(postDto.creatorId).name,
                creatorPicture = mapCreatorToBand(postDto.creatorId).photo,
                text = postDto.text,
                date = postDto.date,
                comments = mapCommentList(postDto.comments),
                attachment = mapAttachment(postDto.attachment),
                creatorType = CreatorType.valueOf(postDto.creatorType),
                likes = postDto.likes,
                isLiked = checkIfPostIsLiked(postDto.likes),
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
                creatorId = postDto.creatorId,
                creatorName = mapCreatorToLocation(postDto.creatorId).name,
                creatorPicture = mapCreatorToLocation(postDto.creatorId).profilePicture,
                text = postDto.text,
                date = postDto.date,
                comments = mapCommentList(postDto.comments),
                attachment = mapAttachment(postDto.attachment),
                creatorType = CreatorType.valueOf(postDto.creatorType),
                likes = postDto.likes,
                isLiked = checkIfPostIsLiked(postDto.likes),
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, postDto.likes.size),
                    StatisticItem(type = StatisticType.COMMENTS, postDto.comments.size)
                ),
            )
            return postItem
        }
    }

    private fun checkIfPostIsLiked(likes:List<String>):Boolean{
        for(like in likes){
            if(like == myUser.id) return true
        }
        return false
    }
    private fun mapCommentList(commentDtoList: List<CommentDto>): List<CommentItem> {
        val result = mutableListOf<CommentItem>()
        for (commentDto in commentDtoList) {
            val commentItem = CommentItem(
                id = commentDto.id,
                user = userMapper.mapUser(commentDto.user),
                post_id = commentDto.postId,
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

    private fun mapAttachment(photoUrlDto: PhotoUrlDto): PhotoUrlItem {
        return PhotoUrlItem(photoUrlDto.id,photoUrlDto.url)
    }
}
