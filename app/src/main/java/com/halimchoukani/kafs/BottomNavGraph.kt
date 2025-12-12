package com.halimchoukani.kafs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halimchoukani.kafs.ui.screen.CartScreen
import com.halimchoukani.kafs.ui.screen.FavoriteScreen
import com.halimchoukani.kafs.ui.screen.HomeScreen
import com.halimchoukani.kafs.ui.screen.NotificationScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userName: String = "User" // pass the current user name if needed
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                paddingValues = paddingValues,
                userName = userName
            )
        }

        composable(route = BottomBarScreen.Fav.route) {
            FavoriteScreen(
                paddingValues = paddingValues
            )
        }

        composable(route = BottomBarScreen.Cart.route) {
            CartScreen(
                paddingValues = paddingValues
            )
        }

        composable(route = BottomBarScreen.Notification.route) {
            NotificationScreen(
                paddingValues = paddingValues
            )
        }
    }
}
