package ru.potemkin.orpheusjetpackcompose.presentation.post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.UploadPhotoUseCase
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class PostCreationViewModel @Inject constructor(
    creatorInfoItem: CreatorInfoItem,
    private val addPostUseCase: AddPostUseCase,
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val getPostListUseCase: GetPostListUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }

    private val _screenState = MutableLiveData<PostCreationScreenState>(PostCreationScreenState.Initial)
    val screenState: LiveData<PostCreationScreenState> = _screenState

    fun createPost(creatorInfoItem: CreatorInfoItem, postContent:String, photoUri:Uri,context: Context){
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            val mimeType = context.contentResolver.getType(photoUri) ?: "application/octet-stream"
            val filePath = photoUri.path ?: throw IllegalArgumentException("Invalid URI")
            val file = File(filePath)

            val photoUrl = uploadPhotoUseCase(file, mimeType)

            val newPostItem = PostItem(
                id = getNewPostId(),
                creatorId = creatorInfoItem.creatorId,
                creatorType = creatorInfoItem.creatorType,
                creatorPicture = creatorInfoItem.creatorPicture,
                creatorName = creatorInfoItem.creatorName,
                text = postContent,
                comments = listOf(),
                date = currentDate,
                attachment = PhotoUrlItem(
                    getNewPostPictureId(),
                    photoUrl
                ),
                likes= listOf<String>(),
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 0), StatisticItem(StatisticType.COMMENTS, 0)
                ),

                )
            addPostUseCase.invoke(newPostItem)
        }
    }

    private fun getNewPostId():String{
        var postList = getPostListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest:Int = 0
        for (i in postList.value){
            indexes.add(i.id)
        }
        for (i in indexes){
            if(largest< i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest=largest+1
        Log.d("CREATEPOST","ID"+largest.toString())
        return largest.toString()
    }

    private fun getNewPostPictureId():String{
        var postList = getPostListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest:Int = 0
        for (i in postList.value){
            indexes.add(i.attachment.id)
        }
        for (i in indexes){
            if(largest< i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest=largest+1
        Log.d("CREATEPOST","PIC"+largest.toString())
        return largest.toString()
    }
}