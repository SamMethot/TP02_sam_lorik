package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomText(text: String, fontSize: TextUnit, color: Color, modifier: Modifier) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        modifier = modifier
    )
}

@Composable
fun CustomImage(painter: Painter, contentDescription: String, modifier: Modifier) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun CustomIcon(
    painter: Painter,
    contentDescription: String,
    tint: Color,
    modifier: Modifier,
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier,
    )
}

@Composable
fun CustomIconVector(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier,
    tint: Color
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}