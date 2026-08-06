package com.noorheroes.car24assignment.core.common.di

import com.noorheroes.car24assignment.core.common.dispatcher.DefaultDispatcherProvider
import com.noorheroes.car24assignment.core.common.dispatcher.DispatcherProvider
import com.noorheroes.car24assignment.core.common.logging.AppLogger
import com.noorheroes.car24assignment.core.common.logging.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider

    @Binds
    @Singleton
    abstract fun bindLogger(impl: AppLogger): Logger
}

@Module
@InstallIn(SingletonComponent::class)
object CommonProviderModule {
    @Provides
    @Singleton
    fun provideAppLogger(): AppLogger = AppLogger()
}
