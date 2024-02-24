package ru.potemkin.orpheusjetpackcompose.data.network

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.potemkin.orpheusjetpackcompose.data.model.geocode.GeocodeResponse

interface GeocodingService {
    @GET("maps/api/geocode/json")
    suspend fun getCoordinatesFromAddress(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeocodeResponse
}