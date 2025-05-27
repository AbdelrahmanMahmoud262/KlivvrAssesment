package com.klivvrassesment.data.local.utils

import android.content.Context
import com.klivvrassesment.data.local.entity.CityDataEntity
import kotlinx.serialization.json.Json

fun Context.loadCitiesFromAssets(): List<CityDataEntity> {
    val json = assets.open("cities.json").bufferedReader().use { it.readText() }
    return Json.decodeFromString(json)
}