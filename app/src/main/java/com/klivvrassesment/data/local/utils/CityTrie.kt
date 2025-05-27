package com.klivvrassesment.data.local.utils

import android.util.Log
import com.klivvrassesment.data.local.entity.CityDataEntity

class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    val cities: MutableList<CityDataEntity> = mutableListOf()
}


class CityTrie {

    private val root = TrieNode()

    fun insert(city: CityDataEntity) {
        root.cities.add(city)
        var node = root
        for (char in city.name.lowercase()) {
            node = node.children.getOrPut(char) { TrieNode() }
            node.cities.add(city)
        }
    }

    fun searchPrefix(query: String): List<CityDataEntity> {
        Log.d("searchPrefix", "query: $query")
        if (query.isEmpty()) return root.cities
        var node = root
        for (char in query.lowercase()) {
            node = node.children[char] ?: return emptyList()
            Log.d("searchPrefix", "query: $query, char: $char, node: ${node.cities.size}")
        }
        return node.cities
    }

}