package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase

class NewsViewModel : ViewModel() {

    private val repository = PostRepositoryImpl

    private val getPostListUseCase = GetPostListUseCase(repository)

    val postList = getPostListUseCase.getPostList()

}