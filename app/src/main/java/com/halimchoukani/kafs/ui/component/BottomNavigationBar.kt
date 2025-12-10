package com.halimchoukani.kafs.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Favorite", "Cart", "Notification")
    val selectedIcons = listOf(
        Icons.Filled.Home,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.Notifications
    )
    val unselectedIcons = listOf(
        Icons.Filled.Home,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.Notifications
    )

    // Define colors based on the image
    val selectedColor = MaterialTheme.colorScheme.primary // Orange/Brown color
    val unselectedColor = MaterialTheme.colorScheme.surface // Light Grey color
    val backgroundColor = Color.White

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = backgroundColor,
        contentColor = selectedColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    indicatorColor = Color.Transparent // Remove the default indicator background
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}