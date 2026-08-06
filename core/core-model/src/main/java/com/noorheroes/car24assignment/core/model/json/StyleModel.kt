package com.noorheroes.car24assignment.core.model.json

import kotlinx.serialization.Serializable

@Serializable
enum class SpacingToken {
    NONE, XS, S, M, L, XL, XXL
}

@Serializable
enum class ColorToken {
    PRIMARY, SECONDARY, SURFACE, BACKGROUND, ERROR, OUTLINE, 
    ON_SURFACE, ON_PRIMARY, ON_BACKGROUND, TRANSPARENT, BLACK, WHITE
}

@Serializable
enum class TypographyToken {
    DISPLAY_LARGE, DISPLAY_MEDIUM, 
    HEADLINE_LARGE, HEADLINE_MEDIUM, HEADLINE_SMALL,
    TITLE_LARGE, TITLE_MEDIUM, TITLE_SMALL,
    BODY_LARGE, BODY_MEDIUM, BODY_SMALL,
    LABEL_LARGE, LABEL_MEDIUM, LABEL_SMALL
}

@Serializable
enum class ShapeToken {
    NONE, XS, S, M, L, XL, FULL
}

@Serializable
data class PaddingModel(
    val top: SpacingToken = SpacingToken.NONE,
    val bottom: SpacingToken = SpacingToken.NONE,
    val start: SpacingToken = SpacingToken.NONE,
    val end: SpacingToken = SpacingToken.NONE,
    val all: SpacingToken? = null
)

@Serializable
data class BackgroundModel(
    val color: ColorToken = ColorToken.TRANSPARENT
)

@Serializable
data class TypographyStyleModel(
    val style: TypographyToken = TypographyToken.BODY_MEDIUM,
    val color: ColorToken = ColorToken.ON_SURFACE
)

@Serializable
data class StyleModel(
    val padding: PaddingModel? = null,
    val margin: PaddingModel? = null,
    val background: BackgroundModel? = null,
    val typography: TypographyStyleModel? = null,
    val shape: ShapeToken? = null,
    val alpha: Float? = null
)
