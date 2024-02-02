package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

class CommentsViewModelFactory(
    private val feedPost: PostItem,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}