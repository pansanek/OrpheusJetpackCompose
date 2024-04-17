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

    fun saveAuthState(authState: AuthState) {
        Log.d("AUTHORIZE", "save "+authState.toString())
        sharedPreferences.edit().putString("auth_state", authState.toString()).apply()
    }

    fun getAuthState(): AuthState {
        val authStateString = sharedPreferences.getString("auth_state", null)
        Log.d("AUTHORIZE", "get "+authStateString)
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
}