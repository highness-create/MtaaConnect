package com.example.mtaaconnect.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mtaaconnect.data.PostViewModel
import com.example.mtaaconnect.ui.theme.screens.SplashScreen
import com.example.mtaaconnect.ui.theme.screens.client.AddClientScreen
import com.example.mtaaconnect.ui.theme.screens.client.updateClientScreen
import com.example.mtaaconnect.ui.theme.screens.clients.ClientListScreen
import com.example.mtaaconnect.ui.theme.screens.dashboards.dashboardScreen
import com.example.mtaaconnect.ui.theme.screens.home.homeScreen
import com.example.mtaaconnect.ui.theme.screens.login.loginScreen
import com.example.mtaaconnect.ui.theme.screens.post.PostScreen
import com.example.mtaaconnect.ui.theme.screens.register.registerScreen
import com.example.mtaaconnect.ui.theme.screens.services.ViewServicesScreen
import com.example.mtaaconnect.ui.theme.screens.user.userScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    val postViewModel: PostViewModel = viewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_USER) {
                    popUpTo(ROUTE_USER) { inclusive = true }
                }
            }
        }

        composable(ROUTE_REGISTER) { registerScreen(navController) }
        composable(ROUTE_LOGIN) { loginScreen(navController) }

        composable(ROUTE_POST) {
            PostScreen(navController, postViewModel)
        }

        composable(ROUTE_DASHBOARD) {
            dashboardScreen(navController, )
        }

        composable(ROUTE_ADD_CLIENT) { AddClientScreen(navController) }
        composable(ROUTE_SERVICES) { ViewServicesScreen(navController) }
        composable(ROUTE_USER) { userScreen(navController) }
        composable(ROUTE_CLIENT_LIST) { ClientListScreen(navController) }
        composable(ROUTE_HOME) { homeScreen(navController, postViewModel) }

        composable(
            ROUTE_UPDATE_CLIENT,
            arguments = listOf(
                navArgument("clientId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId")!!
            updateClientScreen(navController, clientId)
        }
    }
}
