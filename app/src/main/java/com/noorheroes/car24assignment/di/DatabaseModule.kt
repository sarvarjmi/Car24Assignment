package com.noorheroes.car24assignment.di

import android.content.Context
import androidx.room.Room
import com.noorheroes.car24assignment.core.common.constants.AppConstants
import com.noorheroes.car24assignment.core.database.dao.*
import com.noorheroes.car24assignment.core.database.database.SDUIDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SDUIDatabase {
        return Room.databaseBuilder(
            context,
            SDUIDatabase::class.java,
            AppConstants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideScreenDao(db: SDUIDatabase): ScreenDao = db.screenDao()

    @Provides
    fun provideSectionDao(db: SDUIDatabase): SectionDao = db.sectionDao()

    @Provides
    fun provideComponentDao(db: SDUIDatabase): ComponentDao = db.componentDao()

    @Provides
    fun provideConfigDao(db: SDUIDatabase): ConfigDao = db.configDao()

    @Provides
    fun provideSeedHistoryDao(db: SDUIDatabase): SeedHistoryDao = db.seedHistoryDao()
}
