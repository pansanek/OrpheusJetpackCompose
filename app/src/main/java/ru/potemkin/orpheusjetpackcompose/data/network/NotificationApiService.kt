package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.NotificationDto

interface NotificationApiService {

    @GET("/api/notifications/")
    suspend fun getAllNotifications(): List<NotificationDto>

    @POST("/api/notifications/")
    suspend fun createNotification(@Body requestBody: RequestBody): NotificationDto

    @GET("/api/notifications/{id}")
    suspend fun getNotificationById(@Path("id") id: String): NotificationDto

    @PUT("/api/notifications/{id}")
    suspend fun updateNotification(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): NotificationDto
}