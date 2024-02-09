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
    private fun authorize(login: String, password: String) {
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("login", login)
            jsonObject.put("password", password)
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = ApiFactory.appUserApiService.authorize(requestBody)
            if (response == "OK"){
                _authState.value = AuthState.Authorized
            }
        }
    }

    private fun createUser(userItem: UserItem) {
        viewModelScope.launch {
            val user = mapper.mapUserToRequest(userItem)
            val jsonObjectString = user.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiFactory.appUserApiService.createUser(requestBody)
        }
    }

    private fun createAdministrator(administratorItem: AdministratorItem) {
        viewModelScope.launch {
            val admin = adminMapper.mapAdministratorToRequest(administratorItem)
            val jsonObjectString = admin.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiFactory.appAdministratorApiService.createAdministrator(requestBody)
        }
    }

    private fun createMusician(musicianItem: MusicianItem) {
        viewModelScope.launch {
            val musician = musicianMapper.mapMusicianToRequest(musicianItem)
            val jsonObjectString = musician.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiFactory.appMusicianApiService.createMusician(requestBody)
        }
    }

    private fun createLocation(locationItem: LocationItem) {
        viewModelScope.launch {
            val location = locationMapper.mapLocationToRequest(locationItem)
            val jsonObjectString = location.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiFactory.appLocationApiService.createLocation(requestBody)
        }
    }
}