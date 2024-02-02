package ru.potemkin.orpheusjetpackcompose.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindLocationRepository(locationImpl:LocationRepositoryImpl): LocationRepository
    @ApplicationScope
    @Binds
    fun bindMessageRepository(messageImpl: MessageRepositoryImpl): MessageRepository
    @ApplicationScope
    @Binds
    fun bindPostRepository(postImpl: PostRepositoryImpl): PostRepository
    @ApplicationScope
    @Binds
    fun bindUserRepository(userImpl: UserRepositoryImpl): UserRepository


//    companion object {
//
//        @ApplicationScope
//        @Provides
//        fun provideApiService(): ApiService {
//            return ApiFactory.apiService
//        }
//    }
}

