package com.klivvrassesment.domain.repository

import androidx.paging.PagingData
import com.klivvrassesment.domain.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    /**
     * Searches for cities based on the provided query string.
     *
     * This function is designed to be used with pagination, returning a [Flow] of [PagingData]
     * containing [CityEntity] objects that match the search query.
     *
     * @param query The string to search for within city names.
     * @return A [Flow] of [PagingData] containing [CityEntity] objects matching the query.
     */
    suspend fun searchCities(query: String): Flow<PagingData<CityEntity>>

}