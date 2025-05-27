package com.klivvrassesment.domain.repository

import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.domain.entity.CityEntity

interface CityRepository {

    suspend fun searchCountries(query: String): List<CityEntity>

}