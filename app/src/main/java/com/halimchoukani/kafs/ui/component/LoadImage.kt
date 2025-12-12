package com.halimchoukani.kafs.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun LoadImage(url: String, modifier: Modifier, description: String? = null) {
    AsyncImage(
        model = url,
        contentDescription = description,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
