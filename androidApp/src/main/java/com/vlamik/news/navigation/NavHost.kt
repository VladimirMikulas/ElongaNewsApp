package com.vlamik.news.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vlamik.news.features.list.NewsListScreen
import com.vlamik.news.features.login.LoginScreen

@Composable
fun NewsNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.Login.path) {
        composable(NavRoutes.Login.path) {
            LoginScreen(
                hiltViewModel()
            ) { isAuthenticated ->
                navController.navigate(NavRoutes.NewsList.build(isAuthenticated))
            }
        }

        composable(NavRoutes.NewsList.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.IS_AUTHENTICATED_KEY)?.let {
                NewsListScreen(hiltViewModel(), isAuthenticated = it.toBoolean(), { articleId ->
                    navController.navigate(NavRoutes.NewsDetails.build(articleId))
                }) {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
        }/*
        composable(NavRoutes.NewsDetails.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                NewsDetailsScreen(newsDetailsViewModel(newsId = it.toInt()))
            }
        }*/
    }
}
