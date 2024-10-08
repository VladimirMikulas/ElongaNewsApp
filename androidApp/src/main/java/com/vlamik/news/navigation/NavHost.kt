package com.vlamik.news.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NbaNavHost(
    navController: NavHostController = rememberNavController(),
) {
    /*NavHost(navController = navController, startDestination = NavRoutes.NewsList.path) {
        composable(NavRoutes.NewsList.path) {
            NewsListScreen(hiltViewModel()) {
                navController.navigate(NavRoutes.NewsDetails.build(it))
            }
        }
        composable(NavRoutes.NewsDetails.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                NewsDetailsScreen(newsDetailsViewModel(newsId = it.toInt()))
            }
        }
    }*/
}
