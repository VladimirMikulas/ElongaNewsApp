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
fun TemplateNaveHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.List.path) {
        composable(NavRoutes.List.path) {
            PlayerListScreen(hiltViewModel(), openDetailsClicked = {
                navController.navigate(NavRoutes.Details.build(it))
            })
        }
        composable(NavRoutes.Details.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                PlayerDetailsScreen(detailViewModel(playerId = it))
            }
        }
    }
}
