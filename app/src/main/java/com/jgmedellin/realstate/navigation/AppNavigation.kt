package com.jgmedellin.realstate.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgmedellin.realstate.data.repository.AuthRepository
import com.jgmedellin.realstate.ui.screens.activitylogs.ActivityLogsScreen
import com.jgmedellin.realstate.ui.screens.dashboard.DashboardScreen
import com.jgmedellin.realstate.ui.screens.login.LoginScreen
import com.jgmedellin.realstate.ui.screens.maintenance.MaintenanceScreen
import com.jgmedellin.realstate.ui.screens.propertydetail.PropertyDetailScreen
import com.jgmedellin.realstate.ui.screens.register.RegisterScreen
import com.jgmedellin.realstate.ui.screens.settings.SettingsScreen
import com.jgmedellin.realstate.ui.theme.*

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object PropertyDetail : Screen("property/{propertyId}") {
        fun createRoute(propertyId: String) = "property/$propertyId"
    }
    data object ActivityLogs : Screen("activity_logs")
    data object Maintenance : Screen("maintenance")
    data object Register : Screen("register")
    data object Settings : Screen("settings")
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Screen.Dashboard.route, Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem("Maintenance", Screen.Maintenance.route, Icons.Filled.Build, Icons.Outlined.Build),
    BottomNavItem("Properties", Screen.ActivityLogs.route, Icons.Filled.LocationCity, Icons.Outlined.LocationCity),
    BottomNavItem("Settings", Screen.Settings.route, Icons.Filled.Settings, Icons.Outlined.Settings)
)

@Composable
fun AppNavigation(
    isDarkMode: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Show bottom nav only for main screens (not login or register)
    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.route }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryBlue,
                                selectedTextColor = PrimaryBlue,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = PrimaryBlue.copy(alpha = 0.1f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Dashboard.route) {
                Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    DashboardScreen(
                        onPropertyClick = { propertyId ->
                            navController.navigate(Screen.PropertyDetail.createRoute(propertyId))
                        }
                    )
                }
            }

            composable(
                route = Screen.PropertyDetail.route,
                arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
            ) { backStackEntry ->
                val propertyId = backStackEntry.arguments?.getString("propertyId") ?: return@composable
                Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    PropertyDetailScreen(
                        propertyId = propertyId,
                        onBackClick = { navController.popBackStack() },
                        onViewLogs = { navController.navigate(Screen.ActivityLogs.route) }
                    )
                }
            }

            composable(Screen.ActivityLogs.route) {
                Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    ActivityLogsScreen()
                }
            }

            composable(Screen.Maintenance.route) {
                Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    MaintenanceScreen()
                }
            }

            composable(Screen.Settings.route) {
                Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                    SettingsScreen(
                        isDarkMode = isDarkMode,
                        onToggleTheme = onToggleTheme,
                        onLogout = {
                            AuthRepository.signOut()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
