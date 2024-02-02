package com.vlamik.nba.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vlamik.nba.features.details.PlayerDetailsScreen
import com.vlamik.nba.features.list.PlayerListScreen

@Composable
fun NbaNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.PlayerList.path) {
        composable(NavRoutes.PlayerList.path) {
            PlayerListScreen(hiltViewModel()) {
                navController.navigate(NavRoutes.PlayerDetails.build(it))
            }
        }
        composable(NavRoutes.PlayerDetails.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                PlayerDetailsScreen(playerDetailViewModel(playerId = it.toInt()))
            }
        }
    }
}
