package com.klivvrassesment.ui.utils

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.ui.models.CityListItem
import com.klivvrassesment.ui.models.CityUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<PagingData<CityUiModel>>.withAlphabeticalHeaders(): Flow<PagingData<CityListItem>> =
    this
        .map { pagingData ->
            pagingData
                .map { CityListItem.CityItem(it) }
                .insertSeparators<CityListItem.CityItem, CityListItem> { before, after ->
                    when {
                        before == null && after != null ->
                            CityListItem.Header(after.city.name.first().uppercaseChar())

                        before is CityListItem.CityItem && after is CityListItem.CityItem -> {
                            val prev = before.city.name.first().uppercaseChar()
                            val next = after.city.name.first().uppercaseChar()
                            if (prev != next) CityListItem.Header(next) else null
                        }

//                        after != null && before?.city?.name?.first()?.equals(after.city.name.first()) == false -> {
//                            // separator - after is first item that starts with its first letter
//                            CityListItem.Header(after.city.name.first().uppercaseChar())
//                        }

                        else -> null
                    }
                }
        }