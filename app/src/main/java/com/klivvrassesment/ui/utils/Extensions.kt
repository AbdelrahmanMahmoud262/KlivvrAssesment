package com.klivvrassesment.ui.utils

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.klivvrassesment.ui.models.CityListItem
import com.klivvrassesment.ui.models.CityUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension function for a Flow of PagingData containing CityUiModel.
 * It transforms the flow to include alphabetical headers for city names.
 *
 * This function maps each CityUiModel to a CityListItem.CityItem and then inserts
 * CityListItem.Header separators based on the first letter of the city names.
 * - A header is inserted at the beginning with the first letter of the first city.
 * - A header is inserted between cities if their first letters are different.
 * - A special "End" header is inserted at the very end.
 *
 * @return A new Flow of PagingData containing CityListItem, which includes both city items and headers.
 */
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