package com.klivvrassesment.ui.screens.maps

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.klivvrassesment.ui.models.CityUiModel

@Composable
fun MapsScreenRoot(
    modifier: Modifier = Modifier,
    city: CityUiModel
) {

    MapScreen(
        modifier = modifier,
        city = city
    )

}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    city: CityUiModel
) {
    val cairo = LatLng(city.coordinates.lat, city.coordinates.lng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cairo, 12f)
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth(.9f)
            .fillMaxHeight(.7f)
            .clip(MaterialTheme.shapes.medium),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = remember { MarkerState(position = cairo) },
            title = "${city.name}, ${city.country}",
        )
    }
}