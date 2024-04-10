package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import android.util.Log
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
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.AddBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetMyUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import javax.inject.Inject

class BandCreationViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getMyUserBandsUseCase: GetMyUserBandsUseCase,
    private val addBandUseCase: AddBandUseCase,
    private val getBandListUseCase: GetBandListUseCase
) : ViewModel() {

    private val initialState = BandCreationScreenState.Initial

    private val _screenState = MutableLiveData<BandCreationScreenState>(initialState)
    val screenState: LiveData<BandCreationScreenState> = _screenState


    init {
        _screenState.value = BandCreationScreenState.Loading
        loadMyUserBands()
    }

    private fun loadMyUserBands() {
        viewModelScope.launch {
            _screenState.value = BandCreationScreenState.Bands(
                getMyUserBandsUseCase.invoke(getMyUserUseCase.invoke().id)
            )
        }
    }


    fun createBand(name:String,genre:String,photoUrl:String){
        addBandUseCase.invoke(
            BandItem(
                id = getNewBandId(),
                name = name,
                members = listOf(getMyUserUseCase.invoke()),
                genre = genre,
                photo = PhotoUrlItem(
                    id = getNewPictureId(),
                    url = photoUrl
                )
            )
        )
    }

    private fun getNewPictureId():String{
        var postList = getBandListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest:Int = 0
        for (i in postList){
            indexes.add(i.photo.id)
        }
        for (i in indexes){
            if(largest< i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest=largest+1
        Log.d("CREATEPOST","PIC"+largest.toString())
        return largest.toString()
    }
    private fun getNewBandId(): String {
        var bandList = getBandListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in bandList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }



}