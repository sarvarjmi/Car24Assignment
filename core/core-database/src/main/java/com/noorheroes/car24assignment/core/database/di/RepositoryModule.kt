package com.noorheroes.car24assignment.core.database.di

import com.noorheroes.car24assignment.core.database.repository.ComponentRepositoryImpl
import com.noorheroes.car24assignment.core.database.repository.ScreenRepositoryImpl
import com.noorheroes.car24assignment.core.database.repository.SeedRepositoryImpl
import com.noorheroes.car24assignment.core.model.repository.ComponentRepository
import com.noorheroes.car24assignment.core.model.repository.ScreenRepository
import com.noorheroes.car24assignment.core.model.repository.SeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindScreenRepository(impl: ScreenRepositoryImpl): ScreenRepository

    @Binds
    @Singleton
    abstract fun bindComponentRepository(impl: ComponentRepositoryImpl): ComponentRepository

    @Binds
    @Singleton
    abstract fun bindSeedRepository(impl: SeedRepositoryImpl): SeedRepository
}
