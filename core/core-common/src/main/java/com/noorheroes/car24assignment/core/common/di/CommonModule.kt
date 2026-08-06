package com.noorheroes.car24assignment.core.common.di

import com.noorheroes.car24assignment.core.common.dispatcher.DefaultDispatcherProvider
import com.noorheroes.car24assignment.core.common.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
