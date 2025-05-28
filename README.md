# City Search App

This Android application displays and searches a large list of cities with associated metadata and country flags. It uses Jetpack Compose for the UI and leverages Paging 3 to efficiently load and display data. To support fast prefix-based search, it implements a Trie data structure.

## Features

- Efficient prefix-based search using a custom Trie
- Flag display based on country code
- Jetpack Compose UI with Material 3 support
- Pagination using Paging 3 with support for sticky headers
- Light and dark theme support

## Search Logic

To improve search performance over a large dataset (e.g., 200,000+ cities), this project uses a Trie (prefix tree) instead of a linear search.

### Trie Behavior

A Trie stores strings such that common prefixes are shared among nodes. For instance:

- Searching for "A" returns all cities starting with "A"
- Searching for "Al" returns cities like "Alabama" and "Albuquerque"
- Searching for "Alb" returns "Albuquerque" only

### Time Complexity Comparison

- Trie search: O(p), where p is the length of the query prefix
- Linear search: O(n * m), where n is the number of cities and m is the average city name length

This drastically improves performance when working with large datasets.

## Architecture

This project follows a clean architecture structure with three main layers:

### Layers

1. **data/** – Contains data sources (e.g., Trie, paging), models (e.g., `CityDataEntity`), and repositories
2. **domain/** – Includes use cases and interfaces shared across layers
3. **ui/** – Handles presentation logic using Jetpack Compose, ViewModels, and state management

### Paging Source

- A custom `PagingSource` retrieves cities from the Trie based on the current prefix
- Results are grouped alphabetically
- Grouping logic inserts sticky headers alongside city entries, transforming:
  ```kotlin
  List<CityDataEntity> → List<CityListItem> (Header or CityItem)
  ```

## Testing

Unit tests are provided for the Trie implementation in:
```
KlivvrAssesment\app\src\test\java\com\klivvrassesment/TrieUnitTest.kt
```
### These tests verify:

  - Correct insertion of cities

  - Prefix-based search

  - Empty prefix behavior

  - Case-insensitive search support

## Tech Stack

  - Kotlin

  - Jetpack Compose

  - Paging 3

  - Coroutine support

  - Material 3 Design
  - Koin (DI)
  - Coil (Image Loading)

  
