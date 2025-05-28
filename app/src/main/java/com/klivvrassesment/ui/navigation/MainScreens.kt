package com.klivvrassesment.ui.navigation

import kotlinx.serialization.Serializable

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