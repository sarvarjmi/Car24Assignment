package com.noorheroes.car24assignment.core.model.domain

import com.noorheroes.car24assignment.core.designsystem.token.*
import com.noorheroes.car24assignment.core.model.json.*

data class Style(
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
