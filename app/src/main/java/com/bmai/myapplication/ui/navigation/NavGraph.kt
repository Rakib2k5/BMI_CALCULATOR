package com.bmai.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bmai.myapplication.domain.model.BMICategory
import com.bmai.myapplication.domain.model.BMIResult
import com.bmai.myapplication.domain.model.Gender
import com.bmai.myapplication.ui.screens.*
import com.bmai.myapplication.ui.goals.GoalsScreen
import com.bmai.myapplication.ui.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Input : Screen("input")
    object Result : Screen("result/{score}/{weight}/{height}/{age}/{gender}") {
        fun createRoute(score: Double, weight: Double, height: Double, age: Int, gender: String) = 
            "result/$score/$weight/$height/$age/$gender"
    }
    object Dashboard : Screen("dashboard")
    object Goals : Screen("goals")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: BMIViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartClick = { navController.navigate(Screen.Input.route) }
            )
        }
        composable(Screen.Input.route) {
            InputScreen(
                onCalculate = { score, weight, height, age, gender ->
                    val result = BMIResult(
                        score = score,
                        category = BMICategory.fromScore(score),
                        weight = weight,
                        height = height,
                        age = age,
                        gender = Gender.valueOf(gender)
                    )
                    viewModel.saveResult(result)
                    navController.navigate(Screen.Result.createRoute(score, weight, height, age, gender))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Result.route) { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toDoubleOrNull() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDoubleOrNull() ?: 0.0
            val height = backStackEntry.arguments?.getString("height")?.toDoubleOrNull() ?: 0.0
            val age = backStackEntry.arguments?.getString("age")?.toIntOrNull() ?: 0
            val gender = backStackEntry.arguments?.getString("gender") ?: "MALE"
            
            ResultScreen(
                score = score,
                weight = weight,
                height = height,
                age = age,
                gender = gender,
                onBack = { navController.popBackStack() },
                onRetry = { navController.navigate(Screen.Input.route) {
                    popUpTo(Screen.Home.route)
                }}
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.Goals.route) {
            GoalsScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
