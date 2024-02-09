package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferences {
    companion object {
        private var secureSharedPreferences: SharedPreferences? = null
        fun getSecurePreferences(context: Context): SharedPreferences {
            return secureSharedPreferences ?: let {
                val masterKey: MasterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
                secureSharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    "secureSharedPrefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
                secureSharedPreferences!!
            }
        }
    }
}