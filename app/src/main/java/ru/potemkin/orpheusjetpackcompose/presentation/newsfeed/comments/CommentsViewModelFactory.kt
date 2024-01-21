package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

class CommentsViewModelFactory(
    private val feedPost: PostItem,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost, application) as T
    }
}