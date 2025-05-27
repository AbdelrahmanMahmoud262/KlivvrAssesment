package com.klivvrassesment.data.local.utils

import com.klivvrassesment.data.local.entity.CityDataEntity

object TrieNode{
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    val cities: MutableList<CityDataEntity> = mutableListOf()
}


class CountryTrie {

    private val root = TrieNode

    fun insert(country: CityDataEntity){
        var node = root
        for (char in country.name){
            node = node.children.getOrPut(char){TrieNode}
            node.cities.add(country)
        }
    }

    fun searchPrefix(query: String):List<CityDataEntity>{
        var node = root
        for (char in query){
            node = node.children[char] ?: return emptyList()
        }
        return node.cities
    }

}