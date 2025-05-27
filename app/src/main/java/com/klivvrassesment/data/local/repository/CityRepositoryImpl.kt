package com.klivvrassesment.data.local.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.klivvrassesment.data.local.source.CityPagingSource
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
        context.populateTrieFromJsonStream(cityTrie)
    }


    override suspend fun searchCountries(query: String): Flow<PagingData<CityEntity>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            CityPagingSource(
                trie = cityTrie,
                query = query
            )
        }
    ).flow.map { pagingData -> pagingData.map { it.toEntity() } }
}