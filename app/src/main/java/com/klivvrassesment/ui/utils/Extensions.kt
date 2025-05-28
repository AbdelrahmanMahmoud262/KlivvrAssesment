package com.klivvrassesment.ui.utils

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
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
                            CityListItem.Header(after.city.name.first().uppercaseChar().toString())

                        before is CityListItem.CityItem && after is CityListItem.CityItem -> {
                            val prev = before.city.name.first().uppercaseChar()
                            val next = after.city.name.first().uppercaseChar()
                            if (prev != next) CityListItem.Header(next.toString()) else null
                        }

                        after == null -> {
                            CityListItem.Header("End", isLast = true)
                        }

                        else -> null
                    }
                }
        }