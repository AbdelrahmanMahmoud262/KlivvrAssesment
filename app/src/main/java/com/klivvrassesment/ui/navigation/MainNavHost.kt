package com.klivvrassesment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.klivvrassesment.ui.models.CityUiModel
import com.klivvrassesment.ui.screens.main.MainScreenRoot
import com.klivvrassesment.ui.screens.maps.MapsScreenRoot

/**
 * CompositionLocal to provide NavHostController to the composables.
 * This allows access to the NavController from anywhere in the composable tree
 * without explicitly passing it down through parameters.
 * If no NavController is found in the current composition, it will throw an error.
 */
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    CompositionLocalProvider(LocalNavController provides navController) {

        NavHost(
            navController = navController,
            startDestination = MainScreens.MainScreen,
        ) {

            composable<MainScreens.MainScreen> {
                MainScreenRoot(
                    modifier = modifier
                )
            }

            dialog<MainScreens.MapsScreen> {

                val city = it.toRoute<MainScreens.MapsScreen>()

                MapsScreenRoot(
                    modifier = modifier,
                    city = CityUiModel(
                        country = city.country,
                        name = city.name,
                        id = 0,
                        coordinates = CityUiModel.CoordinatesUiModel(
                            lng = city.lng,
                            lat = city.lat
                        )
                    )
                )

            }

        }
    }
}