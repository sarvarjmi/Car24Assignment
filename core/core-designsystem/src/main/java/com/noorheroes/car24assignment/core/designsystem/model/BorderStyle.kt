package com.noorheroes.car24assignment.core.designsystem.model

import com.noorheroes.car24assignment.core.designsystem.token.ColorToken
import com.noorheroes.car24assignment.core.designsystem.token.ShapeToken
import com.noorheroes.car24assignment.core.designsystem.token.SpacingToken

data class BorderStyle(
    val width: SpacingToken = SpacingToken.NONE,
    val color: ColorToken = ColorToken.OUTLINE,
    val shape: ShapeToken = ShapeToken.NONE
)
