package com.bmai.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bmai.myapplication.ui.navigation.NavGraph
import com.bmai.myapplication.ui.navigation.Screen
import com.bmai.myapplication.ui.theme.MyApplicationTheme
import com.bmai.myapplication.ui.theme.PrimaryBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != Screen.Home.route && currentRoute != null) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Input.route || currentRoute.startsWith("result"),
                                    onClick = { 
                                        if (currentRoute != Screen.Input.route) {
                                            navController.navigate(Screen.Input.route) {
                                                popUpTo(Screen.Input.route) { inclusive = true }
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Calculate") },
                                    label = { Text("Calculate") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Dashboard.route,
                                    onClick = { 
                                        if (currentRoute != Screen.Dashboard.route) {
                                            navController.navigate(Screen.Dashboard.route) {
                                                popUpTo(Screen.Input.route)
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                                    label = { Text("History") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Goals.route,
                                    onClick = { 
                                        if (currentRoute != Screen.Goals.route) {
                                            navController.navigate(Screen.Goals.route) {
                                                popUpTo(Screen.Input.route)
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.History, contentDescription = "Goals") },
                                    label = { Text("Goals") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Settings.route,
                                    onClick = { 
                                        if (currentRoute != Screen.Settings.route) {
                                            navController.navigate(Screen.Settings.route) {
                                                popUpTo(Screen.Input.route)
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                                    label = { Text("Settings") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
