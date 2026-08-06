package com.noorheroes.car24assignment.feature.renderer.widget

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.noorheroes.car24assignment.core.designsystem.token.ColorToken
import com.noorheroes.car24assignment.core.designsystem.token.TypographyToken
import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.feature.renderer.resolver.StyleResolver

@Composable
fun TextWidget(component: Component) {
    val text = component.properties["text"] as? String ?: ""
    val maxLines = (component.properties["maxLines"] as? Number)?.toInt() ?: Int.MAX_VALUE
    val textAlignName = component.properties["textAlign"] as? String

    // Priority: properties > style > defaults
    val propTypography = component.properties["typography"] as? String
    val propColor = component.properties["color"] as? String

    val typographyToken = propTypography?.let {
        try { TypographyToken.valueOf(it.uppercase()) } catch (e: Exception) { null }
    } ?: component.style?.typography?.style

    val colorToken = propColor?.let {
        try { ColorToken.valueOf(it.uppercase()) } catch (e: Exception) { null }
    } ?: component.style?.typography?.color

    val textAlign = when (textAlignName?.lowercase()) {
        "center" -> TextAlign.Center
        "end" -> TextAlign.End
        "justify" -> TextAlign.Justify
        else -> TextAlign.Start
    }

    Text(
        text = text,
        modifier = Modifier.then(StyleResolver.resolveModifier(component.style, component.visibility)),
        style = StyleResolver.resolveTypography(typographyToken),
        color = colorToken?.let { StyleResolver.resolveColor(it) } ?: LocalContentColor.current,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign
    )
}
