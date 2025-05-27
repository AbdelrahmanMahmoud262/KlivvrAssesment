package com.klivvrassesment.data.local.di

import com.klivvrassesment.data.local.repository.CityRepositoryImpl
import com.klivvrassesment.data.local.utils.CountryTrie
import com.klivvrassesment.domain.repository.CityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataModule = module {

    singleOf(::CountryTrie)
    singleOf(::CityRepositoryImpl).bind(CityRepository::class)

}