package ru.potemkin.orpheusjetpackcompose.presentation.auth

sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}