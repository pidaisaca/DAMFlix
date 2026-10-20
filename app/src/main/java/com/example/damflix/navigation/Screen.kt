package com.example.damflix.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.damflix.R

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector,
) {
    object Home : Screen(
        route = "home",
        titleResId = R.string.nav_home,
        icon = Icons.Default.Home,
    )

    object Catalog : Screen(
        route = "catalog",
        titleResId = R.string.nav_catalog,
        icon = Icons.AutoMirrored.Filled.List,
    )

    object AddMovie : Screen(
        route = "add_movie",
        titleResId = R.string.nav_add_movie,
        icon = Icons.Default.AddCircle,
    )

    object Profile : Screen(
        route = "profile",
        titleResId = R.string.nav_profile,
        icon = Icons.Default.Person,
    )
}
