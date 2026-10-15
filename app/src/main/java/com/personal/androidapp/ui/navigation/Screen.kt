package com.personal.androidapp.ui.navigation

sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Test1 : Screen("test1")
    data object Test2 : Screen("test2")
}
