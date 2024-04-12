package ru.potemkin.orpheusjetpackcompose.presentation.auth

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.AddLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.AddMusicianUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.AddUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMusiciansListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.SetMyUserUseCase
import java.io.IOException
import java.util.Locale
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val addMusicianUseCase: AddMusicianUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getMusiciansListUseCase: GetMusiciansListUseCase,
    private val getLocationListUseCase: GetLocationListUseCase,
    private val setMyUserUseCase: SetMyUserUseCase
) : ViewModel() {
//    private val mapper = UsersMapper()
//    private val adminMapper = AdministratorMapper()
//    private val musicianMapper = MusicianMapper()
//    private val locationMapper = LocationMapper()

    private val initialState = AuthState.Initial

    private val _authState = MutableLiveData<AuthState>(initialState)
    var authState: LiveData<AuthState> = _authState

    init {
        _authState.value = AuthState.NotAuthorized
    }

    fun authorize(login: String, password: String) {
        val userList = getUserListUseCase.invoke()
        for(i in userList){
            if(login == i.login && password ==i.password){
                _authState.value = AuthState.Authorized
                Log.d("AUTHORIZE",_authState.value.toString())
                setMyUserUseCase.invoke(i)
            }
        }
    }


    fun createMusician(
        about: String,
        email: String,
        login: String,
        name: String,
        password: String,
        userType: String,
        genre: String,
        instrument: String,
    ) {
        val newUser = UserItem(
            id = getNewUserId(),
            login = login,
            name = name,
            password = password,
            email = email,
            about = about,
            user_type = UserType.valueOf(userType),

            )
        Log.d("AUTHORIZAAAA",newUser.toString())
        addUserUseCase.invoke(
            newUser
        )
        addMusicianUseCase.invoke(
            MusicianItem(
                id = getNewMusicianId(),
                user = newUser,
                genre = genre,
                instrument = instrument
            )
        )
        setMyUserUseCase.invoke(newUser)
    }

    fun createAdminAndLocation(about: String,
                               email: String,
                               login: String,
                               name: String,
                               password: String,
                               userType: String,
                               locationName: String,
                               locationAddress: String,
                               locationAbout: String,
                               context: Context
                               ) {
        val newUser = UserItem(
            id = getNewUserId(),
            login = login,
            name = name,
            password = password,
            email = email,
            about = about,
            user_type = UserType.valueOf(userType),

            )
        addUserUseCase.invoke(
            newUser
        )
        addLocationUseCase(
            LocationItem(
                id = getNewLocationId(),
                admin = newUser,
                name = locationName,
                about = locationAbout,
                address = locationAddress,
                latitude = convertAddressToLatLng(locationAddress,context).first,
                longitude = convertAddressToLatLng(locationAddress,context).second,
            )
        )
        setMyUserUseCase.invoke(newUser)
    }


    fun convertAddressToLatLng(address: String,context:Context): Pair<Double, Double> {
        val geocoder = Geocoder(context)
        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val latitude = addresses[0].latitude
                    val longitude = addresses[0].longitude
                    return Pair(latitude, longitude)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Pair(0.0,0.0)
    }


    private fun getNewUserId(): String {
        var userList = getUserListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in userList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        Log.d("CREATEPOST", "ID" + largest.toString())
        return largest.toString()
    }

    private fun getNewMusicianId(): String {
        var musicianList = getMusiciansListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in musicianList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        Log.d("CREATEPOST", "ID" + largest.toString())
        return largest.toString()
    }
    private fun getNewLocationId(): String {
        var locationList = getLocationListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in locationList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        Log.d("CREATEPOST", "ID" + largest.toString())
        return largest.toString()
    }

//    private fun checkLogin(context: Context):Boolean {
//        if(isUserIdAvailable(context)) return false
//        _authState.value = AuthState.Authorized
//        return true
//    }
//
//    fun authorize(login: String, password: String) {
//        viewModelScope.launch {
//            val jsonObject = JSONObject()
//            jsonObject.put("login", login)
//            jsonObject.put("password", password)
//            val jsonObjectString = jsonObject.toString()
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            val response = ApiFactory.appUserApiService.authorize(requestBody)
//            if (response != "NOT OK") {
//                userId = response
//                _authState.value = AuthState.Authorized
//            }
//
//        }
//    }
//
//
//    fun createMusician(about: String,
//                       email: String,
//                       login: String,
//                       name: String,
//                       password: String,
//                       userType: String,
//                       genre: String,
//                       instrument: String,
//                       ) {
//        viewModelScope.launch {
//            val user = mapper.mapUserToRequest(about,email,login,name,password,userType)
//            val jsonObjectString = user.toString()
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            val userResponse = ApiFactory.appUserApiService.createUser(requestBody)
//
//            val musician = musicianMapper.mapMusicianToRequest(mapper.mapUser(userResponse),
//                genre, instrument)
//            val musicianJsonObjectString = musician.toString()
//            val musicianRequestBody =
//                musicianJsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            ApiFactory.appMusicianApiService.createMusician(musicianRequestBody)
//
//            _authState.value = AuthState.Authorized
//        }
//    }
//
//    fun createAdminAndLocation(about: String,
//                               email: String,
//                               login: String,
//                               name: String,
//                               password: String,
//                               userType: String,
//                               locationName: String,
//                               locationAddress: String,
//                               locationAbout: String
//                               ) {
//        viewModelScope.launch {
//            val user = mapper.mapUserToRequest(about,email,login,name,password,userType)
//            val jsonObjectString = user.toString()
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            val userResponse = ApiFactory.appUserApiService.createUser(requestBody)
//
//
//            val location = locationMapper.mapLocationToRequest(
//                mapper.mapUser(userResponse),
//                locationName,
//                locationAddress,
//                locationAbout,
//            )
//            val locationJsonObjectString = location.toString()
//            val locationRequestBody =
//                locationJsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            val locationResponse =
//                ApiFactory.appLocationApiService.createLocation(locationRequestBody)
//
//
//
//
//            _authState.value = AuthState.Authorized
//        }
//    }
}