package com.noorheroes.car24assignment.core.navigation.di

import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideAppNavigator(): AppNavigator = AppNavigator()
}
