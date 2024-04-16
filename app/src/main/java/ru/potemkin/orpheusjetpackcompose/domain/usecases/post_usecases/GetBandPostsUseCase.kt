package ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

//class GetBandPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
//    operator fun invoke(bandId: String): StateFlow<List<PostItem>> {
//        return postRepository.getBandPosts(bandId)
//    }
//}