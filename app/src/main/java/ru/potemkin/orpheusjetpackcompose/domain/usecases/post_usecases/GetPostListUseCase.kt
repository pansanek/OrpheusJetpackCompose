package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(private val repository: PostRepository) {
    operator fun invoke(): StateFlow<List<PostItem>> {
        return repository.getPostsList()
    }
}

