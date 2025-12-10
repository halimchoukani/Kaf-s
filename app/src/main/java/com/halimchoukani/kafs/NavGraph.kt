package com.halimchoukani.kafs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halimchoukani.kafs.ui.screen.LoginScreen
import com.halimchoukani.kafs.ui.screen.MainScreen
import com.halimchoukani.kafs.ui.screen.SignUpScreen
import com.halimchoukani.kafs.ui.screen.SplashScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController =navController,
        startDestination = Screen.Splash.route)
    {
        composable(
            route = Screen.Splash.route
        ){
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route
        ){
            LoginScreen(   navController = navController         )
        }

        composable(
            route = Screen.SignUp.route
        ){
            SignUpScreen(navController = navController)
        }
        composable(
            route = Screen.MainScreen.route
        ){
            MainScreen()
        }
    }
}