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
enum class ArrangementToken {
    START, END, CENTER, SPACE_BETWEEN, SPACE_AROUND, SPACE_EVENLY
}

@Serializable
data class ArrangementModel(
    val horizontal: ArrangementToken = ArrangementToken.START,
    val vertical: ArrangementToken = ArrangementToken.START
)

@Serializable
enum class AlignmentToken {
    START, CENTER, END, TOP, BOTTOM
}

@Serializable
data class AlignmentModel(
    val horizontal: AlignmentToken? = null,
    val vertical: AlignmentToken? = null
)

@Serializable
data class LayoutParamsModel(
    val weight: Float? = null,
    val fillMaxWidth: Boolean? = null,
    val fillMaxHeight: Boolean? = null
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
    val border: BorderModel? = null,
    val width: SizeToken? = null,
    val height: SizeToken? = null,
    val arrangement: ArrangementModel? = null,
    val alignment: AlignmentModel? = null,
    val layoutParams: LayoutParamsModel? = null
)
