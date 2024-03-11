package com.example.kmm_app.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object HomeScreen : Destinations(
        route = "home",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    data object AllWorkouts : Destinations(
        route = "allWorkouts",
        title = "All Workouts",
        icon = Icons.Outlined.List
    )
}
