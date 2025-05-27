package com.klivvrassesment.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.klivvrassesment.R
import com.klivvrassesment.ui.models.CityListItem
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

private val HEADER_SIZE = 60.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    searchQuery: String,
    cities: LazyPagingItems<CityListItem>,
    onEvent: (MainUiEvent) -> Unit
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            )
            .padding(top = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = stringResource(R.string.city_search),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.cities, cities.itemCount),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            for (index in 0 until cities.itemCount) {
                val cityListItem = cities.peek(index)

                val itemKey: Any = cityListItem?.let { item ->
                    when (item) {
                        is CityListItem.CityItem -> "C-${item.city.id}"
                        is CityListItem.Header -> "H-${item.letter}"
                    }
                } ?: "placeholder-$index"

                val contentType: Any? = cityListItem?.let { item ->
                    when (item) {
                        is CityListItem.CityItem -> "CityItemType"
                        is CityListItem.Header -> "HeaderItemType"
                    }
                } ?: "PlaceholderType"

                if (cityListItem != null) {
                    when (cityListItem) {
                        is CityListItem.CityItem -> {
                            item(key = itemKey, contentType = contentType) {
                               CityItem(
                                   city = (cities[index] as CityListItem.CityItem),
                               )
                            }
                        }

                        is CityListItem.Header -> {
                            stickyHeader(key = itemKey, contentType = contentType) {
                                HeaderItem(
                                    header = cityListItem
                                )
                            }
                        }
                    }
                }
            }

            item {
                if (cities.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(Modifier.padding(16.dp),color = Color.Cyan)
                }
            }

            item {
                if (cities.loadState.append is LoadState.NotLoading) {
                    CircularProgressIndicator(Modifier.padding(16.dp),color = Color.Blue)
                }
            }
            item {
                if (cities.loadState.append is LoadState.Error) {
                    CircularProgressIndicator(Modifier.padding(16.dp),color = Color.Red)
                }
            }
        }

    }


}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: CityListItem.CityItem
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(HEADER_SIZE),
            contentAlignment = Alignment.Center
        ){

            MainDivider(
                modifier = Modifier
                    .fillMaxHeight()
            )

        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            Text(
                text = "${city.city.name}, ${city.city.country}",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }

}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    header: CityListItem.Header
) {

    Column(
        modifier = modifier
            .width(HEADER_SIZE)
    ) {

        Box(
            modifier = Modifier
                .size(HEADER_SIZE)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = header.letter.uppercaseChar().toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        MainDivider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

    }

}

@Composable
private fun MainDivider(
    modifier: Modifier = Modifier,
) {
    VerticalDivider(
        modifier = modifier
            .height(20.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onSurface
    )
}