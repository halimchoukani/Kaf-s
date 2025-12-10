package com.halimchoukani.kafs.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.halimchoukani.kafs.BottomBarScreen
import com.halimchoukani.kafs.BottomNavGraph
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomBar(navController = navController)}
    ) {paddingValues ->
        BottomNavGraph(navController = navController, paddingValues = paddingValues)
    }
}


@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Fav,
        BottomBarScreen.Cart,
        BottomBarScreen.Notification
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .shadow(6.dp, RoundedCornerShape(24.dp)),
        // 1. Set the background to your primary background color (White)
        containerColor = MaterialTheme.colorScheme.background,
        // Optional: Add a subtle elevation/shadow
        tonalElevation = 8.dp
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    // Use Copper for the selected icon, and a neutral color (OnSurface) for unselected
    val iconColor = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    val icon = if(selected) painterResource(screen.filledIcon) else painterResource(screen.icon)
    NavigationBarItem(
        // Remove the 'label' composable entirely to match the design (no text under icon)
        label = null,
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 1. Icon
                Icon(
                    painter = icon,
                    contentDescription = screen.title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp) // Standard icon size
                )

                // 2. Custom Indicator (The colored dot)
                if (selected) {
                    Spacer(modifier = Modifier.size(4.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary) // Copper dot
                    )
                }
            }
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        // 3. Customize NavigationBarItem defaults
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.secondary, // Copper (though we override this above)
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            indicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.0f) // Keep the background clear/transparent
        )
    )
}
