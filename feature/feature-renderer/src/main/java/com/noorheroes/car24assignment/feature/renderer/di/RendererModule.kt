package com.noorheroes.car24assignment.feature.renderer.di

import com.noorheroes.car24assignment.core.model.domain.Component
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
            register("banner") { component -> 
                (component as? Component.Banner)?.let { BannerWidget(it) } 
            }
            register("search_bar") { component -> 
                (component as? Component.SearchBar)?.let { SearchBarWidget(it) } 
            }
            register("categories") { component -> 
                (component as? Component.Categories)?.let { CategoriesWidget(it) } 
            }
            register("header") { component -> 
                (component as? Component.Header)?.let { HeaderWidget(it) } 
            }
            register("hero_banner") { component -> 
                (component as? Component.HeroBanner)?.let { HeroBannerWidget(it) } 
            }
            register("car_card") { component -> 
                (component as? Component.CarCard)?.let { CarCardWidget(it) } 
            }
            register("horizontal_rail") { component -> 
                (component as? Component.HorizontalRail)?.let { HorizontalRailWidget(it) } 
            }
            register("cta") { component -> 
                (component as? Component.Cta)?.let { CtaWidget(it) } 
            }
            register("footer") { component -> 
                (component as? Component.Footer)?.let { FooterWidget(it) } 
            }
            register("column") { component -> ColumnWidget(component) }
            register("row") { component -> RowWidget(component) }
        }
    }

    @Provides
    @Singleton
    fun provideActionDispatcher(navigator: AppNavigator): ActionDispatcher = ActionDispatcher(navigator)
}
