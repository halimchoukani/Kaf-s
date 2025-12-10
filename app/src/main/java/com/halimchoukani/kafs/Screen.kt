package com.halimchoukani.kafs

sealed class Screen(val route : String) {
    object Splash : Screen(route = "splash_screen")
    object Login : Screen(route = "login_screen")
    object SignUp : Screen(route="signup_screen")
    object MainScreen :Screen(route = "main_screen")
}