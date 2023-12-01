package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(private val postRepository: PostRepository) {
    fun getPostList(): List<PostItem>{
        Log.d("POSTS","Usecase: ${postRepository.getPostsList()}")
        return postRepository.getPostsList()
    }
}