package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(private val postRepository: PostRepository){
    suspend operator fun invoke(PostItem: PostItem){
        postRepository.deletePostItem(PostItem)
    }
}