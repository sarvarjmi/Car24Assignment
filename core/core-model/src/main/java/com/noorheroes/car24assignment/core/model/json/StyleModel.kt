package com.noorheroes.car24assignment.core.model.json

import com.noorheroes.car24assignment.core.designsystem.token.*
import kotlinx.serialization.Serializable

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
    val color: ColorToken = ColorToken.TRANSPARENT,
    val gradient: List<ColorToken>? = null
)

@Serializable
data class TypographyStyleModel(
    val style: TypographyToken = TypographyToken.BODY_MEDIUM,
    val color: ColorToken = ColorToken.ON_SURFACE
)

@Serializable
data class BorderModel(
    val width: SpacingToken = SpacingToken.NONE,
    val color: ColorToken = ColorToken.OUTLINE,
    val shape: ShapeToken = ShapeToken.NONE
)

@Serializable
data class StyleModel(
    val padding: PaddingModel? = null,
    val margin: PaddingModel? = null,
    val background: BackgroundModel? = null,
    val typography: TypographyStyleModel? = null,
    val shape: ShapeToken? = null,
    val alpha: Float? = null,
    val elevation: ElevationToken? = null,
    val border: BorderModel? = null
)
