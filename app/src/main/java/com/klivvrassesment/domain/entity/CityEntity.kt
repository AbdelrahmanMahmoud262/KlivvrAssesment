package com.klivvrassesment.domain.entity

import com.klivvrassesment.data.local.entity.CityDataEntity
import java.util.Locale

data class CityEntity(
    val country: String,
    val name:String,
    val id:Int,
    val coordinates: Coordinates
){

    data class Coordinates(
        val lon:Double,
        val lat:Double
    )

}

// City mapper
fun CityDataEntity.toEntity() = CityEntity(
    country = country,
    name = name,
    id = id,
    coordinates = CityEntity.Coordinates(
        lon = coordinates.lon,
        lat = coordinates.lat
    )
)