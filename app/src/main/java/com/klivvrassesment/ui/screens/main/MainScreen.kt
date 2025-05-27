package com.klivvrassesment.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.klivvrassesment.ui.models.CityUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {

    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()

    MainScreen(
        modifier = modifier,
        searchQuery = searchQuery.value,
        cities = viewModel.cities.collectAsLazyPagingItems(),
        onEvent = viewModel::onEvent,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    searchQuery: String,
    cities:LazyPagingItems<CityUiModel>,
    onEvent: (MainUiEvent) -> Unit
) {



}