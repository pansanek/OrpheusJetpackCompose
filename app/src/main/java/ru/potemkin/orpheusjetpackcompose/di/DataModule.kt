package ru.potemkin.orpheusjetpackcompose.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.network.BandApiService
import ru.potemkin.orpheusjetpackcompose.data.network.ChatApiService
import ru.potemkin.orpheusjetpackcompose.data.network.CommentApiService
import ru.potemkin.orpheusjetpackcompose.data.network.LocationApiService
import ru.potemkin.orpheusjetpackcompose.data.network.MessageApiService
import ru.potemkin.orpheusjetpackcompose.data.network.MusicianApiService
import ru.potemkin.orpheusjetpackcompose.data.network.PostApiService
import ru.potemkin.orpheusjetpackcompose.data.network.UserApiService
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MusicianRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindLocationRepository(locationImpl:LocationRepositoryImpl): LocationRepository

    @ApplicationScope
    @Binds
    fun bindPostRepository(postImpl: PostRepositoryImpl): PostRepository
    @ApplicationScope
    @Binds
    fun bindUserRepository(userImpl: UserRepositoryImpl): UserRepository

    @ApplicationScope
    @Binds
    fun bindChatRepository(chatImpl: ChatRepositoryImpl): ChatRepository

    @ApplicationScope
    @Binds
    fun bindBandRepository(bandImpl: BandRepositoryImpl): BandRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideAppUserApiService(): UserApiService {
            return ApiFactory.appUserApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppPostApiService(): PostApiService {
            return ApiFactory.appPostApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppBandApiService(): BandApiService {
            return ApiFactory.appBandApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppMusicianApiService(): MusicianApiService {
            return ApiFactory.appMusicianApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppChatApiService(): ChatApiService {
            return ApiFactory.appChatApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppMessageApiService(): MessageApiService {
            return ApiFactory.appMessageApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppLocationApiService(): LocationApiService {
            return ApiFactory.appLocationApiService
        }

        @ApplicationScope
        @Provides
        fun provideAppCommentApiService(): CommentApiService {
            return ApiFactory.appCommentApiService
        }



    }
}

