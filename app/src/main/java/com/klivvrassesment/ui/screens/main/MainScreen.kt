package com.klivvrassesment.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.klivvrassesment.R
import com.klivvrassesment.ui.models.CityListItem
import com.klivvrassesment.ui.navigation.LocalNavController
import com.klivvrassesment.ui.navigation.MainScreens
import com.klivvrassesment.ui.utils.Constants.getFlagUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreenRoot(
    modifier: Modifier = Modifier, viewModel: MainViewModel = koinViewModel()
) {

    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    MainScreen(
        modifier = modifier,
        searchQuery = searchQuery.value,
        cities = viewModel.cities.collectAsLazyPagingItems(),
        onEvent = viewModel::onEvent,
        onCityClick = {
            navController.navigate(
                MainScreens.MapsScreen(
                    lat = it.city.coordinates.lat,
                    lng = it.city.coordinates.lng,
                    name = it.city.name,
                    country = it.city.country
                )
            )
        }
    )

}

private val HEADER_SIZE = 60.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    searchQuery: String,
    cities: LazyPagingItems<CityListItem>,
    onCityClick: (CityListItem.CityItem) -> Unit,
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
            modifier = Modifier.weight(1f)
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
                                    onCityClick = { onCityClick(cityListItem) }
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
                    CircularProgressIndicator(Modifier.padding(16.dp), color = Color.Cyan)
                }
            }

            item {
                if (cities.loadState.append is LoadState.NotLoading) {
                    CircularProgressIndicator(Modifier.padding(16.dp), color = Color.Blue)
                }
            }
            item {
                if (cities.loadState.append is LoadState.Error) {
                    CircularProgressIndicator(Modifier.padding(16.dp), color = Color.Red)
                }
            }
        }

    }


}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: CityListItem.CityItem,
    onCityClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(HEADER_SIZE)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {

            MainDivider(
                modifier = Modifier
                    .fillMaxHeight()
            )

        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = 8.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .clickable(
                    onClick = onCityClick
                )
                .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AsyncImage(
                model = city.city.country.getFlagUrl(),
                contentDescription = null,
                onError = {
                    it.result.throwable.printStackTrace()
                },
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(24.dp)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "${city.city.name}, ${city.city.country}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "${city.city.coordinates.lat}, ${city.city.coordinates.lng}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier, header: CityListItem.Header
) {

    Column(
        modifier = modifier.width(HEADER_SIZE)
    ) {

        Box(
            modifier = Modifier
                .size(HEADER_SIZE)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.extraLarge
                ), contentAlignment = Alignment.Center
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
                .height(20.dp)
        )

    }

}

@Composable
private fun MainDivider(
    modifier: Modifier = Modifier,
) {
    VerticalDivider(
        modifier = modifier, thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground
    )
}