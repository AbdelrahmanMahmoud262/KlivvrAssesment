package com.klivvrassesment.data.local.repository

import android.content.Context
import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.data.local.utils.CountryTrie
import com.klivvrassesment.data.local.utils.loadCitiesFromAssets
import com.klivvrassesment.domain.entity.CityEntity
import com.klivvrassesment.domain.entity.toEntity
import com.klivvrassesment.domain.repository.CityRepository

class CityRepositoryImpl(
    context: Context,
    private val countryTrie: CountryTrie
): CityRepository {

    private val countries = context.loadCitiesFromAssets()

    init {
        loadCountries()
    }

    private fun loadCountries(){
        countries.forEach {
            countryTrie.insert(it)
        }
    }

    override suspend fun searchCountries(query: String): List<CityEntity> {
        return countryTrie.searchPrefix(query).map { it.toEntity() }
    }
}