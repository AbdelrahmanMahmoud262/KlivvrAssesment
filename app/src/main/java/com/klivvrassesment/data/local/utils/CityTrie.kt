package com.klivvrassesment.data.local.utils

import com.klivvrassesment.data.local.entity.CityDataEntity

/**
 * Represents a node in the Trie data structure.
 * Each node can have multiple children, represented by a map where the key is a character
 * and the value is the child TrieNode.
 * Each node also stores a list of cities whose names pass through this node.
 */
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    val cities: MutableList<CityDataEntity> = mutableListOf()
}


/**
 * A Trie data structure specifically designed for storing and searching [CityDataEntity] objects
 * based on their names.
 *
 * This Trie allows for efficient prefix-based searching of city names.
 * - Insertion of a city takes O(L) time, where L is the length of the city name.
 * - Searching for cities with a given prefix takes O(P) time, where P is the length of the prefix.
 *
 * Each node in the Trie can store a list of cities that share the prefix represented by the path
 * from the root to that node.
 */
class CityTrie {

    private val root = TrieNode()

    /**
     * Inserts a city into the Trie.
     *
     * The city is added to the root node's list of cities, and then traversed through the Trie
     * based on its name (converted to lowercase). At each character in the city's name,
     * if a corresponding child node doesn't exist, it's created. The city is then added
     * to the list of cities for that node.
     *
     * @param city The [CityDataEntity] to insert into the Trie.
     */
    fun insert(city: CityDataEntity) {
        root.cities.add(city)
        var node = root
        for (char in city.name.lowercase()) {
            node = node.children.getOrPut(char) { TrieNode() }
            node.cities.add(city)
        }
    }

    /**
     * Searches for cities in the trie that have names starting with the given query prefix.
     *
     * The search is case-insensitive. If the query is empty, it returns all cities stored in the trie.
     *
     * @param query The prefix string to search for.
     * @return A list of [CityDataEntity] objects whose names start with the query prefix.
     *         Returns an empty list if no cities match the prefix or if the prefix is invalid.
     *         Returns all cities if the query is empty.
     */
    fun searchPrefix(query: String): List<CityDataEntity> {
        if (query.isEmpty()) return root.cities
        var node = root
        for (char in query.lowercase()) {
            node = node.children[char] ?: return emptyList()
        }
        return node.cities
    }

}