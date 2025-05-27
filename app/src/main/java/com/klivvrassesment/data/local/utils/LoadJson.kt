package com.klivvrassesment.data.local.utils

import android.content.Context
import android.util.JsonReader
import android.util.Log
import com.klivvrassesment.data.local.entity.CityDataEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStreamReader

//
//fun Context.loadCitiesFromAssets(): List<CityDataEntity> {
//    val json = assets.open("cities.json").bufferedReader().use { it.readText() }
//    return Json.decodeFromString(json)
//}

@OptIn(ExperimentalSerializationApi::class)
fun Context.populateTrieFromJsonStream(cityTrie: CityTrie) {
    var count = 0
    try {
        assets.open("cities.json").use { inputStream ->
           val cities = Json.decodeFromStream<List<CityDataEntity>>(inputStream)

            cities.forEach {
                cityTrie.insert(it)
                count++
            }
        }
        Log.d("JsonParsing", "Successfully parsed and inserted $count cities into Trie.")
    } catch (e: Exception) {
        Log.e("JsonParsing", "Error parsing JSON stream for Trie", e)
    }
}