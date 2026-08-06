package com.noorheroes.car24assignment.feature.renderer.di

import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.renderer.widget.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RendererModule {

    @Provides
    @Singleton
    fun provideComponentRegistry(): ComponentRegistry {
        return ComponentRegistry().apply {
            register("banner") { component -> BannerWidget(component) }
            register("search_bar") { component -> SearchBarWidget(component) }
            register("categories") { component -> CategoriesWidget(component) }
            register("column") { component -> ColumnWidget(component) }
            register("row") { component -> RowWidget(component) }
        }
    }

    @Provides
    @Singleton
    fun provideActionDispatcher(navigator: AppNavigator): ActionDispatcher = ActionDispatcher(navigator)
}
