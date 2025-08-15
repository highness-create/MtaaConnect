package com.example.mtaaconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mtaaconnect.ui.theme.screens.client.AddClientScreen
import com.example.mtaaconnect.ui.theme.screens.client.ClientListScreen
import com.example.mtaaconnect.ui.theme.screens.client.updateClientScreen
import com.example.mtaaconnect.ui.theme.screens.dashboards.dashboardScreen
import com.example.mtaaconnect.ui.theme.screens.login.loginScreen
import com.example.mtaaconnect.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_REGISTER
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(ROUTE_REGISTER) { registerScreen(navController) }
        composable(ROUTE_LOGIN) { loginScreen(navController) }
        composable(ROUTE_DASHBOARD) { dashboardScreen(navController) }
        composable(ROUTE_ADD_CLIENT) { AddClientScreen(navController) }
        composable(ROUTE_CLIENT_LIST) {ClientListScreen (navController)}
        composable(ROUTE_UPDATE_CLIENT,
            arguments = listOf(
                navArgument("clientId")
            {type = NavType.StringType}))
        {
                backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId")!!
//            updateClientScreen(navController, clientId) }


    }
}}
