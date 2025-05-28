package com.klivvrassesment.data.local.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.klivvrassesment.data.local.paging.CityPagingSource
import com.klivvrassesment.data.local.utils.CityTrie
import com.klivvrassesment.data.local.utils.populateTrieFromJsonStream
import com.klivvrassesment.domain.entity.CityEntity
import com.klivvrassesment.domain.entity.toEntity
import com.klivvrassesment.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityRepositoryImpl(
    context: Context,
    private val cityTrie: CityTrie
) : CityRepository {


    init {
        // Load the data from the JSON file into the Trie
        context.populateTrieFromJsonStream(cityTrie)
    }


    override suspend fun searchCities(query: String): Flow<PagingData<CityEntity>> = Pager(
        config = PagingConfig(
            pageSize = 50,
            initialLoadSize = 20,
            // Enabling placeholders to be able to accumulate the total data size without being loaded yet
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            CityPagingSource(
                trie = cityTrie,
                query = query
            )
        }
    ).flow.map { pagingData -> pagingData.map { it.toEntity() } }
}