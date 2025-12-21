package com.halimchoukani.kafs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.halimchoukani.kafs.ui.screen.CartScreen
import com.halimchoukani.kafs.ui.screen.CoffeeDetailScreen
import com.halimchoukani.kafs.ui.screen.FavoriteScreen
import com.halimchoukani.kafs.ui.screen.HomeScreen
import com.halimchoukani.kafs.ui.screen.NotificationScreen
import com.halimchoukani.kafs.ui.screen.ProfileScreen
import com.halimchoukani.kafs.viewmodel.UserViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userName: String = "User",
    onLogout: () -> Unit = {},
    userViewModel: UserViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                paddingValues = paddingValues,
                userName = userName,
                onLogout = onLogout,
                navController = navController
            )
        }
        composable(
            route = Screen.Profile.route
        ){
            ProfileScreen(
                paddingValues = paddingValues,
                userViewModel = userViewModel,
                navController = navController
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

        composable(
            route = Screen.CoffeeDetail.route,
            arguments = listOf(navArgument("coffeeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coffeeId = backStackEntry.arguments?.getString("coffeeId") ?: ""
            CoffeeDetailScreen(
                coffeeId = coffeeId,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}