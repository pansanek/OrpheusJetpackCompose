package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke() {
        repository.loadNextData()
    }
}