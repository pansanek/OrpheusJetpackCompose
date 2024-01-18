package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import java.util.UUID

interface LocationApiService {

    @GET("/api/locations/")
    suspend fun getAllLocations(): List<LocationDto>

    @POST("/api/locations/")
    suspend fun createLocation(@Body requestBody: RequestBody): LocationDto

    @GET("/api/locations/{id}")
    suspend fun getLocationById(@Path("id") id: UUID): LocationDto
}