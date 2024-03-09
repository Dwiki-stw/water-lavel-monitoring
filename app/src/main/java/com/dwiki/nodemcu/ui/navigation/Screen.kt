package com.dwiki.nodemcu.ui.navigation

sealed class Screen(var route: String) {
    object Splash : Screen("splash_screen")
    object Monitoring: Screen("monitoring_screen")
}
