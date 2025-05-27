package com.klivvrassesment.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.klivvrassesment.domain.entity.CityEntity

@Stable
@Immutable
data class CityUiModel(
    val country: String,
    val name: String,
    val id: Int,
    val coordinates: CoordinatesUiModel
){
    data class CoordinatesUiModel(
        val lon: Double,
        val lat: Double
    )
}

// City Ui Model Mapper
fun CityEntity.toUiModel() = CityUiModel(
    country = country.toString(),
    name = name,
    id = id,
    coordinates = CityUiModel.CoordinatesUiModel(
        lon = coordinates.lon,
        lat = coordinates.lat
    )
)