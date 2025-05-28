package com.klivvrassesment.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the different screens that can be navigated to within the main part of the application.
 * This sealed interface is used for type-safe navigation and allows for passing arguments
 * to specific screens.
 */
sealed interface MainScreens {

    @Serializable
    data object MainScreen : MainScreens

    @Serializable
    data class MapsScreen(
        val lat: Double,
        val lng: Double,
        val name: String,
        val country: String
    ) : MainScreens

}