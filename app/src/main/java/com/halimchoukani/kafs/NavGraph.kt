package com.halimchoukani.kafs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halimchoukani.kafs.ui.screen.LoginScreen
import com.halimchoukani.kafs.ui.screen.MainScreen
import com.halimchoukani.kafs.ui.screen.ProfileScreen
import com.halimchoukani.kafs.ui.screen.SignUpScreen
import com.halimchoukani.kafs.ui.screen.SplashScreen
import com.halimchoukani.kafs.viewmodel.SplashViewModel
import com.halimchoukani.kafs.viewmodel.UserViewModel


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
            val vm: SplashViewModel = viewModel()

            when (vm.isLoggedIn.value) {
                true -> navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
                false -> navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }

                else -> {

                }
            }
        }
        composable(
            route = Screen.Splash.route
        ){
            SplashScreen(   navController = navController         )
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
            val userViewModel: UserViewModel = viewModel()
            val userName by userViewModel.userName
            MainScreen(
                userName = userName,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}