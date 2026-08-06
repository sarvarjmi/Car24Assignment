package com.noorheroes.car24assignment.feature.renderer.di

import com.noorheroes.car24assignment.core.common.logging.Logger
import com.noorheroes.car24assignment.core.domain.usecase.UpdateComponentUseCase
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.navigation.navigator.AppNavigator
import com.noorheroes.car24assignment.feature.renderer.action.ActionDispatcher
import com.noorheroes.car24assignment.feature.renderer.action.ActionValidator
import com.noorheroes.car24assignment.feature.renderer.registry.ComponentRegistry
import com.noorheroes.car24assignment.feature.renderer.widget.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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
            register("text") { component -> TextWidget(component) }
            register("image") { component -> ImageWidget(component) }
            register("spacer") { component -> SpacerWidget(component) }
            register("box") { component -> BoxWidget(component) }
            register("search_bar") { component -> 
                (component as? Component.SearchBar)?.let { SearchBarWidget(it) } 
            }
            register("search") { component -> 
                (component as? Component.SearchBar)?.let { SearchBarWidget(it) } 
            }
            register("Search") { component -> 
                (component as? Component.SearchBar)?.let { SearchBarWidget(it) } 
            }
            register("divider") { component -> DividerWidget(component) }
            register("chip") { component -> ChipWidget(component) }
            register("chip_group") { component -> ChipGroupWidget(component) }
            register("categories") { component -> 
                (component as? Component.Categories)?.let { CategoriesWidget(it) } 
            }
            register("header") { component -> 
                (component as? Component.Header)?.let { HeaderWidget(it) } 
            }
            register("hero_banner") { component -> 
                (component as? Component.HeroBanner)?.let { HeroBannerWidget(it) } 
            }
            register("icon") { component -> IconWidget(component) }
            register("badge") { component -> BadgeWidget(component) }
            register("button") { component -> ButtonWidget(component) }
            register("car_card") { component -> 
                (component as? Component.CarCard)?.let { CarCardWidget(it) } 
            }
            register("horizontal_rail") { component -> 
                (component as? Component.HorizontalRail)?.let { HorizontalRailWidget(it) } 
            }
            register("cta") { component -> 
                (component as? Component.Cta)?.let { CtaWidget(it) } 
            }
            register("cta_section") { component -> CTASectionWidget(component) }
            register("card") { component -> CardWidget(component) }
            register("footer") { component -> 
                (component as? Component.Footer)?.let { FooterWidget(it) } 
            }
            register("grid") { component -> GridWidget(component) }
            register("column") { component -> ColumnWidget(component) }
            register("lazy_column") { component -> LazyColumnWidget(component) }
            register("lazy_row") { component -> LazyRowWidget(component) }
            register("row") { component -> RowWidget(component) }
        }
    }

    @Provides
    @Singleton
    fun provideActionDispatcher(
        navigator: AppNavigator,
        getComponentJsonUseCase: com.noorheroes.car24assignment.core.domain.usecase.GetComponentJsonUseCase,
        updateComponentUseCase: UpdateComponentUseCase,
        actionValidator: ActionValidator,
        logger: Logger,
        json: Json
    ): ActionDispatcher = ActionDispatcher(navigator, getComponentJsonUseCase, updateComponentUseCase, actionValidator, logger, json)
}
