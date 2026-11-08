package com.example.damflix.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.damflix.DAMFlixApplication
import com.example.damflix.R
import com.example.damflix.ui.screens.CatalogScreen
import com.example.damflix.ui.screens.HomeScreen
import com.example.damflix.ui.screens.MovieDetailScreen
import com.example.damflix.ui.screens.MovieFormScreen
import com.example.damflix.ui.screens.UserProfileScreen
import com.example.damflix.ui.viewmodel.MovieDetailViewModel
import com.example.damflix.ui.viewmodel.MovieDetailViewModelFactory
import com.example.damflix.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

val navigationDrawerItems = listOf(
    Screen.Home,
    Screen.Catalog,
    Screen.AddMovie,
    Screen.Profile,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    movieViewModel: MovieViewModel = viewModel(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreen = navigationDrawerItems.find { it.route == currentRoute } ?: Screen.Home

    ModalNavigationDrawer(
        modifier = modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                navigationDrawerItems.forEach { screen ->
                    val isSelected = currentRoute == screen.route
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = stringResource(id = screen.titleResId),
                            )
                        },
                        label = { Text(text = stringResource(id = screen.titleResId)) },
                        selected = isSelected,
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = currentScreen.titleResId)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.nav_home),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Catalog.route) {
                    CatalogScreen(
                        viewModel = movieViewModel,
                        onMovieClick = { movieId ->
                            navController.navigate(Screen.Detail.createRoute(movieId))
                        },
                    )
                }
                composable(Screen.AddMovie.route) {
                    MovieFormScreen(viewModel = movieViewModel)
                }
                composable(Screen.Profile.route) {
                    UserProfileScreen()
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(
                        navArgument("movieId") {
                            type = NavType.IntType
                        },
                    ),
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                    val context = LocalContext.current
                    val app = context.applicationContext as DAMFlixApplication

                    val detailViewModel: MovieDetailViewModel = viewModel(
                        factory = MovieDetailViewModelFactory(app.repository),
                    )

                    MovieDetailScreen(
                        movieId = movieId,
                        viewModel = detailViewModel,
                        onBackClick = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
