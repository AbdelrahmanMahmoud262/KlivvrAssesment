package com.klivvrassesment.domain.repository

import androidx.paging.PagingData
import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.domain.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun searchCountries(query: String): Flow<PagingData<CityEntity>>

}