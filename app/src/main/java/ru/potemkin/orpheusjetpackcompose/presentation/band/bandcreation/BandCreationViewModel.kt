package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import javax.inject.Inject

class BandCreationViewModel @Inject constructor() : ViewModel() {

    private val initialState = BandCreationScreenState.Initial

    private val _screenState = MutableLiveData<BandCreationScreenState>(initialState)
    val screenState: LiveData<BandCreationScreenState> = _screenState

    private val repository = BandRepositoryImpl()
    private val userRepository = UserRepositoryImpl()

    init {
        _screenState.value = BandCreationScreenState.Loading
        loadMyUserBands()
    }

    private fun loadMyUserBands() {
        viewModelScope.launch {
//            val bands = repository.loadBands()
//            val myUser = userRepository.getMyUser()
//            val myUserBands = mutableListOf<BandItem>()
//            for (band: BandItem in bands) {
//                if (myUser in band.members)
//                    myUserBands.add(band)
//            }
            _screenState.value = BandCreationScreenState.Bands(addBandMockData())
        }
    }

    fun addBandMockData(): List<BandItem> {
        val mockData = mutableListOf<BandItem>(
            BandItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "Bad Omens",
                listOf(
                    UserItem(
                        "51bdc118-e76b-4372-8678-6822658cefed",
                        "noahbadomens",
                        "Noah Sebastian",
                        "12341234",
                        "email@gmail.com",
                        "Vocalist for Bad Omens",
                        UserType.MUSICIAN,
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                            "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                        ),
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                            "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                        ),
                        UserSettingsItem(true, true)
                    ),
                    UserItem(
                        "51bdc118-e76b-4372-8678-6822658cefed",
                        "pansanek",
                        "Sasha",
                        "12341234",
                        "email@gmail.com",
                        "Hehe",
                        UserType.MUSICIAN,
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                            "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                        ),
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                            "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                        ),
                        UserSettingsItem(true, true)
                    )
                ),
                "Metalcore",
                PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                )
            )
        )
        return mockData
    }


//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}