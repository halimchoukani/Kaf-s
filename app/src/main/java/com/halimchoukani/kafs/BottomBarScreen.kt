package com.halimchoukani.kafs

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val filledIcon:Int
) {
    data object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.home,
        filledIcon = R.drawable.homefill
    )

    data object Fav : BottomBarScreen(
        route = "favorites",
        title = "Favorite",
        icon = R.drawable.heart,
        filledIcon = R.drawable.heartfil
    )

    data object Cart : BottomBarScreen(
        route = "cart",
        title = "Cart",
        icon = R.drawable.bag,
        filledIcon = R.drawable.bagfill
    )

    data object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.profile,
        filledIcon = R.drawable.profile_fill
    )
}
