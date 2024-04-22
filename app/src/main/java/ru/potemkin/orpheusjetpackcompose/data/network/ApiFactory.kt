package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // Замените "10.0.2.2" на IP-адрес вашего Docker-хоста, если вы используете эмулятор Android.
    private val baseUrl = "http://10.0.2.2:"

    val appBandApiService: BandApiService = createBandService(baseUrl, 81)
    val appChatApiService: ChatApiService = createChatService(baseUrl, 82)
    val appCommentApiService: CommentApiService = createCommentService(baseUrl, 83)
    val appLocationApiService: LocationApiService = createLocationService(baseUrl, 84)
    val appMessageApiService: MessageApiService = createMessageService(baseUrl, 85)
    val appMusicianApiService: MusicianApiService = createMusicianService(baseUrl, 86)
    val appNotificationApiService: NotificationApiService = createNotificationService(baseUrl, 87)
    val appPostApiService: PostApiService = createPostService(baseUrl, 88)
    val appUserApiService: UserApiService = createUserService(baseUrl, 89)


    private fun createUserService(baseUrl: String, port: Int): UserApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(UserApiService::class.java)
    }

    fun createGeocodingService(): GeocodingService {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingService::class.java)
    }
    private fun createPostService(baseUrl: String, port: Int): PostApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(PostApiService::class.java)
    }

    private fun createMusicianService(baseUrl: String, port: Int): MusicianApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(MusicianApiService::class.java)
    }

    private fun createLocationService(baseUrl: String, port: Int): LocationApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(LocationApiService::class.java)
    }

    private fun createBandService(baseUrl: String, port: Int): BandApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(BandApiService::class.java)
    }

    private fun createCommentService(baseUrl: String, port: Int): CommentApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(CommentApiService::class.java)
    }

    private fun createChatService(baseUrl: String, port: Int): ChatApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ChatApiService::class.java)
    }
    private fun createMessageService(baseUrl: String, port: Int): MessageApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(MessageApiService::class.java)
    }

    private fun createNotificationService(baseUrl: String, port: Int): NotificationApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl$port/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(NotificationApiService::class.java)
    }
}
