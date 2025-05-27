package com.klivvrassesment.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDataEntity(
    val country: String,
    val name: String,
    @SerialName("_id")
    val id: Int,
    @SerialName("coord")
    val coordinates: CoordinatesDataEntity
) {
    @Serializable
    data class CoordinatesDataEntity(
        val lon: Double,
        val lat: Double
    )
}
