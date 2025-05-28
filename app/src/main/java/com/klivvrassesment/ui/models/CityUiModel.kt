package com.klivvrassesment.ui.models

import android.os.Bundle
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.navigation.NavType
import com.klivvrassesment.domain.entity.CityEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Stable
@Immutable
@Serializable
data class CityUiModel(
    val country: String,
    val name: String,
    val id: Int,
    val coordinates: CoordinatesUiModel
){
    @Serializable
    data class CoordinatesUiModel(
        val lng: Double,
        val lat: Double
    )
}

// NavType
val CityUiModelNavType = object : NavType<CityUiModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): CityUiModel {
        val json = bundle.getString(key)
        return Json.decodeFromString(json!!)
    }

    override fun parseValue(value: String): CityUiModel {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: CityUiModel) {
        bundle.putString(key, Json.encodeToString(value))
    }
}

// City Ui Model Mapper
fun CityEntity.toUiModel() = CityUiModel(
    country = country.toString(),
    name = name,
    id = id,
    coordinates = CityUiModel.CoordinatesUiModel(
        lng = coordinates.lon,
        lat = coordinates.lat
    )
)