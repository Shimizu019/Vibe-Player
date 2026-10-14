package com.example.vibesplayer.ui.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.vibesplayer.ui.collection.CollectionScreen
import com.example.vibesplayer.ui.components.BottomTabBar
import com.example.vibesplayer.ui.components.MainTab
import com.example.vibesplayer.ui.home.HomeScreen
import com.example.vibesplayer.ui.home.RecentlyPlayedScreen
import com.example.vibesplayer.ui.library.LibraryScreen
import com.example.vibesplayer.ui.nowplaying.NowPlayingScreen
import com.example.vibesplayer.ui.search.SearchScreen
import com.example.vibesplayer.ui.search.SearchSectionScreen
import com.example.vibesplayer.ui.settings.SettingsScreen
import com.example.vibesplayer.ui.theme.VibesColors

/**
 * App shell: three tabs, each keeping its own back stack (saved and restored when
 * switching), plus the mini player slot above the tab bar. The mini player is hidden
 * until a queue exists (milestone 3) and on Now Playing.
 */
@Composable
fun VibesApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val onNowPlaying = currentDestination?.hasRoute<NowPlayingRoute>() == true

    // Back on a non-Home tab root goes to Home first; on Home it exits (spec section 9).
    BackHandler(
        enabled = !onNowPlaying &&
            currentDestination?.hasRoute<HomeRoute>() == false &&
            navController.previousBackStackEntry == null,
    ) {
        navController.navigateToTab(HomeRoute)
    }

    Scaffold(
        containerColor = VibesColors.Espresso,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (!onNowPlaying) {
                val selectedTab = when {
                    currentDestination?.hasRoute<HomeRoute>() == true -> MainTab.Home
                    currentDestination?.hasRoute<SearchRoute>() == true -> MainTab.Search
                    else -> MainTab.Library
                }
                BottomTabBar(
                    selected = selectedTab,
                    onSelect = { tab ->
                        when (tab) {
                            MainTab.Home -> navController.navigateToTab(HomeRoute)
                            MainTab.Search -> navController.navigateToTab(SearchRoute)
                            MainTab.Library -> navController.navigateToTab(LibraryRoute)
                        }
                    },
                )
            }
        },
    ) { padding ->
        VibesNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding),
        )
    }
}

private fun NavHostController.navigateToTab(route: Any) {
    navigate(route) {
        popUpTo(HomeRoute) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun VibesNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(tween(250)) { it / 4 } + fadeIn(tween(250))
        },
        exitTransition = { fadeOut(tween(250)) },
        popEnterTransition = { fadeIn(tween(250)) },
        popExitTransition = {
            slideOutHorizontally(tween(250)) { it / 4 } + fadeOut(tween(250))
        },
    ) {
        composable<HomeRoute> { HomeScreen() }
        composable<SearchRoute> { SearchScreen() }
        composable<LibraryRoute> { LibraryScreen() }
        composable<NowPlayingRoute> {
            NowPlayingScreen(onClose = { navController.popBackStack() })
        }
        composable<RecentlyPlayedRoute> {
            RecentlyPlayedScreen(onBack = { navController.popBackStack() })
        }
        composable<SettingsRoute> {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable<CollectionRoute> { entry ->
            val route = entry.toRoute<CollectionRoute>()
            CollectionScreen(
                kind = route.kind,
                onBack = { navController.popBackStack() },
            )
        }
        composable<SearchSectionRoute> { entry ->
            val route = entry.toRoute<SearchSectionRoute>()
            SearchSectionScreen(
                section = route.section,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
