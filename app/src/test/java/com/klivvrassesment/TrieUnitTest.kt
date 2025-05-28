package com.klivvrassesment

import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.data.local.utils.CityTrie
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Test class for [CityTrie].
 * This class contains unit tests to verify the functionality of the [CityTrie] data structure,
 * specifically its ability to insert [CityDataEntity] objects and perform prefix-based searches.
 *
 * The tests cover various scenarios, including:
 * - Searching with an empty prefix.
 * - Searching with single-letter and multi-letter prefixes.
 * - Case-insensitive searching.
 * - Searching for non-existent prefixes.
 * - Ensuring the order of results matches the insertion order when appropriate.
 */
internal class CityTrieTest {

    private lateinit var trie: CityTrie
    private val cities = listOf(
        CityDataEntity(name = "Alabama", country = "US", id = 1, coordinates = CityDataEntity.CoordinatesDataEntity(1.0, 2.0)),
        CityDataEntity(name = "Albuquerque", country = "US", id = 2, coordinates = CityDataEntity.CoordinatesDataEntity(1.0, 2.0)),
        CityDataEntity(name = "Anaheim", country = "US", id = 3, coordinates = CityDataEntity.CoordinatesDataEntity(1.0, 2.0)),
        CityDataEntity(name = "Arizona", country = "US", id = 4, coordinates = CityDataEntity.CoordinatesDataEntity(1.0, 2.0)),
        CityDataEntity(name = "Sydney", country = "AU", id = 5, coordinates = CityDataEntity.CoordinatesDataEntity(1.0, 2.0))
    )

    /**
     * Sets up the test environment before each test method.
     *
     * This method initializes a new [CityTrie] and inserts a predefined list of [CityDataEntity]
     * objects into it. This ensures that each test starts with a consistent and populated Trie.
     */
    @Before
    fun setUp() {
        trie = CityTrie()
        cities.forEach { trie.insert(it) }
    }

    /**
     * Tests that searching with an empty prefix string returns all cities in the Trie.
     * This ensures that when no specific prefix is provided, the search defaults
     * to returning all entries, maintaining their original insertion order.
     */
    @Test
    fun `search empty returns all cities`() {
        val result = trie.searchPrefix("")
        // order should be same as insertion
        assertEquals(cities, result)
    }

    /**
     * Tests the prefix search functionality of the Trie with a single letter prefix.
     *
     * This test verifies that when searching for a single-letter prefix (e.g., "A"),
     * the Trie correctly returns all words in the `cities` list that start with that letter.
     * It asserts that the `result` of the search matches the `expected` list of cities.
     */
    @Test
    fun singleLetterSearch() {
        val result = trie.searchPrefix("A")
        val expected = listOf(
            cities[0], // Alabama
            cities[1], // Albuquerque
            cities[2], // Anaheim
            cities[3]  // Arizona
        )
        assertEquals(expected, result)
    }

    /**
     * Tests that searching for the lowercase letter "s" returns a list containing only "Sydney".
     * This verifies that the prefix search is case-insensitive and correctly identifies words
     * starting with the given prefix.
     */
    @Test
    fun lowercaseSearch() {
        val result = trie.searchPrefix("s")
        assertEquals(listOf(cities[4]), result)
    }

    /**
     * Tests the `searchPrefix` method of the Trie.
     * It verifies that when searching for the prefix "Al",
     * the method correctly returns a list containing only "Alabama" and "Albuquerque".
     */
    @Test
    fun `search prefix Al returns only Alabama and Albuquerque`() {
        val result = trie.searchPrefix("Al")
        val expected = listOf(
            cities[0], // Alabama
            cities[1]  // Albuquerque
        )
        assertEquals(expected, result)
    }

    /**
     * Tests the `searchPrefix` method of the Trie.
     * It verifies that when searching for the prefix "Alb", the method correctly returns a list
     * containing only "Albuquerque", assuming "Albuquerque" is present in the Trie and is the
     * only word starting with "Alb" (or the test setup ensures this condition).
     */
    @Test
    fun `search prefix Alb returns only Albuquerque`() {
        val result = trie.searchPrefix("Alb")
        assertEquals(listOf(cities[1]), result)
    }

    /**
     * Tests that searching for a prefix that does not exist in the Trie returns an empty list.
     * It inserts some words into the Trie and then searches for a prefix ("Z")
     * that is known not to be present. The assertion checks if the returned list of words
     * is indeed empty.
     */
    @Test
    fun `search nonexistent prefix returns empty list`() {
        val result = trie.searchPrefix("Z")
        assertTrue(result.isEmpty())
    }

    /**
     * Tests that the search functionality of the Trie is case-insensitive.
     * It inserts a city name and then searches for its prefix using both
     * lowercase and uppercase versions of the prefix.
     * It asserts that both searches return the same, correct city.
     */
    @Test
    fun `search is case insensitive`() {
        val resultUpper = trie.searchPrefix("ar")
        val resultLower = trie.searchPrefix("AR")
        assertEquals(listOf(cities[3]), resultUpper)
        assertEquals(listOf(cities[3]), resultLower)
    }
}