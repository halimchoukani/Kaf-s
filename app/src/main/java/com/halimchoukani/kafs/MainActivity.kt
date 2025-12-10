package com.halimchoukani.kafs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.NavHostController
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.halimchoukani.kafs.ui.screen.LoginScreen
import com.halimchoukani.kafs.ui.screen.MainScreen
import com.halimchoukani.kafs.ui.screen.SplashScreen
import com.halimchoukani.kafs.ui.theme.KafésTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KafésTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}