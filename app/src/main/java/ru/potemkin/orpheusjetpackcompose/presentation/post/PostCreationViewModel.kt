package ru.potemkin.orpheusjetpackcompose.presentation.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import javax.inject.Inject


class PostCreationViewModel @Inject constructor(
    creatorInfoItem: CreatorInfoItem
) : ViewModel() {

    private val repository = PostRepositoryImpl()

    private val _screenState = MutableLiveData<PostCreationScreenState>(PostCreationScreenState.Initial)
    val screenState: LiveData<PostCreationScreenState> = _screenState

    init {

    }


}