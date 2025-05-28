package com.klivvrassesment.data.local.utils

import android.content.Context
import android.util.Log
import com.klivvrassesment.data.local.entity.CityDataEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream


/**
 * Populates a [CityTrie] with data from a JSON file ("cities.json") located in the assets folder.
 *
 * This function reads the JSON file as a stream, decodes it into a list of [CityDataEntity] objects,
 * sorts them by name, and then inserts each city into the provided [CityTrie].
 * It logs the number of successfully parsed and inserted cities, or logs an error if parsing fails.
 *
 * @receiver The [Context] used to access the assets.
 * @param cityTrie The [CityTrie] instance to populate.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Context.populateTrieFromJsonStream(cityTrie: CityTrie) {
    var count = 0
    try {
        assets.open("cities.json").use { inputStream ->
           val cities = Json.decodeFromStream<List<CityDataEntity>>(inputStream)
               .sortedBy { it.name }

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