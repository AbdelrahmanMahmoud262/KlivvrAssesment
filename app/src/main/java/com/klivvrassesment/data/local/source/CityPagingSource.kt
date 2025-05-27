package com.klivvrassesment.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klivvrassesment.data.local.entity.CityDataEntity
import com.klivvrassesment.data.local.utils.CityTrie
import org.koin.core.context.startKoin

class CityPagingSource(
    private val trie: CityTrie,
    private val query: String
) : PagingSource<Int, CityDataEntity>() {

    private val allMatches by lazy { trie.searchPrefix(query) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityDataEntity> {
        val page = params.key ?: 0
        val pageSize = params.loadSize

        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, allMatches.size)

        val pageData = if (fromIndex >= allMatches.size) emptyList()
        else allMatches.subList(fromIndex, toIndex)

        return LoadResult.Page(
            data = pageData,
            itemsBefore = fromIndex,
            itemsAfter = allMatches.count() - toIndex,
            prevKey = if (page == 0) null else page - 1,
            nextKey = if (toIndex < allMatches.size) page + 1 else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CityDataEntity>): Int? {
        return state.anchorPosition?.div(state.config.pageSize)
    }
}