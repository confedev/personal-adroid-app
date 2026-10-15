package com.personal.androidapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.personal.androidapp.data.repository.DownloadRepository
import com.personal.androidapp.ui.menu.MenuScreen
import com.personal.androidapp.ui.menu.MenuViewModel
import com.personal.androidapp.ui.test1.Test1Screen
import com.personal.androidapp.ui.test1.Test1ViewModel
import com.personal.androidapp.ui.test2.Test2Screen
import com.personal.androidapp.ui.test2.Test2ViewModel

@Composable
fun AppNavHost(
    repository: DownloadRepository,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            val menuViewModel = MenuViewModel()
            MenuScreen(
                viewModel = menuViewModel,
                onNavigateToTest1 = { navController.navigate(Screen.Test1.route) },
                onNavigateToTest2 = { navController.navigate(Screen.Test2.route) }
            )
        }
        composable(Screen.Test1.route) {
            val test1ViewModel = Test1ViewModel(repository)
            Test1Screen(
                viewModel = test1ViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Test2.route) {
            val test2ViewModel = Test2ViewModel(repository)
            Test2Screen(
                viewModel = test2ViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
