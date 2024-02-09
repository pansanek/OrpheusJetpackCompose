package ru.potemkin.orpheusjetpackcompose.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.potemkin.orpheusjetpackcompose.data.mappers.AdministratorMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MusicianMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.AdministratorItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val mapper = UsersMapper()
    private val adminMapper = AdministratorMapper()
    private val musicianMapper = MusicianMapper()
    private val locationMapper = LocationMapper()

    private val initialState = AuthState.Initial

    private val _authState = MutableLiveData<AuthState>(initialState)
    val authState: LiveData<AuthState> = _authState

    init {
        _authState.value = AuthState.NotAuthorized
        checkLogin()
    }

    private fun checkLogin() {
        TODO()
        _authState.value = AuthState.Authorized

    }

    fun authorize(login: String, password: String) {
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("login", login)
            jsonObject.put("password", password)
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = ApiFactory.appUserApiService.authorize(requestBody)
            if (response == "OK") {
                _authState.value = AuthState.Authorized
            }
        }
    }


    fun createMusician(about: String,
                       email: String,
                       login: String,
                       name: String,
                       password: String,
                       userType: String,
                       genre: String,
                       instrument: String,
                       ) {
        viewModelScope.launch {
            val user = mapper.mapUserToRequest(about,email,login,name,password,userType)
            val jsonObjectString = user.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val userResponse = ApiFactory.appUserApiService.createUser(requestBody)

            val musician = musicianMapper.mapMusicianToRequest(mapper.mapUser(userResponse),
                genre, instrument)
            val musicianJsonObjectString = musician.toString()
            val musicianRequestBody =
                musicianJsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiFactory.appMusicianApiService.createMusician(musicianRequestBody)

            _authState.value = AuthState.Authorized
        }
    }

    fun createAdminAndLocation(about: String,
                               email: String,
                               login: String,
                               name: String,
                               password: String,
                               userType: String,
                               locationName: String,
                               locationAddress: String,
                               locationAbout: String
                               ) {
        viewModelScope.launch {
            val user = mapper.mapUserToRequest(about,email,login,name,password,userType)
            val jsonObjectString = user.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val userResponse = ApiFactory.appUserApiService.createUser(requestBody)


            val location = locationMapper.mapLocationToRequest(
                mapper.mapUser(userResponse),
                locationName,
                locationAddress,
                locationAbout,
            )
            val locationJsonObjectString = location.toString()
            val locationRequestBody =
                locationJsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val locationResponse =
                ApiFactory.appLocationApiService.createLocation(locationRequestBody)




            _authState.value = AuthState.Authorized
        }
    }
}