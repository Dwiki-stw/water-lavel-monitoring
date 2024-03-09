package com.dwiki.nodemcu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dwiki.nodemcu.ui.navigation.Screen
import com.dwiki.nodemcu.ui.screen.SplashScreen
import com.dwiki.nodemcu.ui.screen.monitoring.MonitoringScreen
import com.dwiki.nodemcu.ui.screen.monitoring.MonitoringViewModel
import com.dwiki.nodemcu.utils.ConnectivityObserver

@Composable
fun MonitoringApp(
    status: ConnectivityObserver.Status,
    monitoringVM: MonitoringViewModel
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route){
            SplashScreen {
                navHostController.navigate(Screen.Monitoring.route){
                    popUpTo(navHostController.graph.id){
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.Monitoring.route){
            MonitoringScreen(status, monitoringVM)
        }
    }
}