package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object ApiFactory {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // Замените "10.0.2.2" на IP-адрес вашего Docker-хоста, если вы используете эмулятор Android.
    private val baseUrl = "http://10.0.2.2:"

    val appUserApiService: ApiService = createService(baseUrl, 88)
    val appPostApiService: ApiService = createService(baseUrl, 87)
    val appMusicianApiService: ApiService = createService(baseUrl, 86)
    val appLocationApiService: ApiService = createService(baseUrl, 85)
    val appCommentApiService: ApiService = createService(baseUrl, 84)
    val appBandApiService: ApiService = createService(baseUrl, 83)
    val appAdministratorApiService: ApiService = createService(baseUrl, 80)

    private fun createService(baseUrl: String, port: Int): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
