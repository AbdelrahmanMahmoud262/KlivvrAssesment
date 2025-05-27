package com.klivvrassesment.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class CityDataEntity(
    val country: String,
    val name: String,
    val id: Int,
    val coordinates: CoordinatesDataEntity
) {
    @Serializable
    data class CoordinatesDataEntity(
        val lon: Double,
        val lat: Double
    )
}
