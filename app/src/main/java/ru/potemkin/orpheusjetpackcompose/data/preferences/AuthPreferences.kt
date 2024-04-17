package ru.potemkin.orpheusjetpackcompose.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthState
import javax.inject.Inject

class AuthPreferences @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "auth_preferences",
        Context.MODE_PRIVATE
    )

    fun saveAuthState(authState: AuthState,id:String) {
        Log.d("AUTHORIZE", "save "+authState.toString())
        sharedPreferences.edit().putString("auth_state", authState.toString()).apply()
        sharedPreferences.edit().putString("myuser_id", id).apply()
    }

    fun getAuthState(): AuthState {
        val authStateString = sharedPreferences.getString("auth_state", null)
        return if (authStateString != null) {
            when (authStateString) {
                AuthState.Authorized.toString() -> AuthState.Authorized
                AuthState.NotAuthorized.toString() -> AuthState.NotAuthorized
                else -> AuthState.NotAuthorized
            }
        } else {
            AuthState.NotAuthorized
        }
    }

    fun getUserId(): String {
        val userIdString = sharedPreferences.getString("myuser_id", null)
        return if (userIdString != null) {
            userIdString
        } else ""
    }
}