package com.klivvrassesment.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.klivvrassesment.ui.utils.Constants.HEADER_SIZE
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
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(
                R.string.cities,
                cities.itemSnapshotList.count { it is CityListItem.CityItem || it == null }),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .imePadding()
        ) {

            val listState = rememberLazyListState()
            val focusManager = LocalFocusManager.current

            LaunchedEffect(listState.isScrollInProgress) {
                // Clearing the focus when the user starts scrolling to hide the search bar keyboard if in focus
                if (listState.isScrollInProgress) focusManager.clearFocus()
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                /**
                 * Iterating through the loaded cities then generating the appropriate key, deciding the content type and content for each item
                 * whether it is a header[HeaderItem] or a city[CityItem]
                 *
                 * @see CityListItem
                 * @see CityListItem.Header
                 * @see CityListItem.CityItem
                 */
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
            }

            // Showing a loading indicator if the list is initially loading
            this@Column.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.Center),
                visible = cities.loadState.refresh is LoadState.Loading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            // Hiding the search footer when the list is scrolled down to achieve a more natural scrolling experience
            this@Column.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                visible = !listState.canScrollBackward || (listState.canScrollForward && listState.lastScrolledBackward),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                SearchBarFooter(
                    query = searchQuery,
                    onEvent = onEvent
                )
            }

        }
    }


}

/**
 * A Composable function that displays a search bar at the bottom of the screen.
 * It includes a text field for entering search queries and a clear button that appears
 * when the query is not blank.
 *
 * @param modifier The modifier to be applied to the search bar.
 * @param query The current search query string.
 * @param onEvent A lambda function to be invoked when a UI event occurs, such as a change in the search query.
 */
@Composable
fun SearchBarFooter(
    modifier: Modifier = Modifier,
    query: String,
    onEvent: (MainUiEvent) -> Unit
) {

    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 16.dp
            )
            .padding(
                top = 24.dp,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier
                .weight(1f),
            value = query,
            onValueChange = { onEvent(MainUiEvent.OnSearchQueryChange(it)) },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium,
        )

        AnimatedVisibility(
            visible = query.isNotBlank(),
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            IconButton(
                onClick = {
                    onEvent(MainUiEvent.OnSearchQueryChange(""))
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}

/**
 * Composable function to display a city item in the list.
 * It shows the city's flag, name, country, and coordinates.
 *
 * @param modifier Modifier for this composable.
 * @param city The [CityListItem.CityItem] to display.
 * @param onCityClick Lambda function to be invoked when the city item is clicked.
 */
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

/**
 * Composable function to display a header item in the list.
 * It shows a letter in a circular background and a vertical divider below it,
 * unless it's the last header.
 *
 * @param modifier Modifier for this composable.
 * @param header The [CityListItem.Header] data to display.
 */
@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    header: CityListItem.Header
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
                text = header.letter.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        MainDivider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(if (header.isLast) 0.dp else 20.dp)
        )

    }

}

/**
 * A composable function that displays a vertical divider with a thickness of 1.dp and
 * color from the MaterialTheme.colorScheme.onBackground.
 *
 * @param modifier The modifier to be applied to the divider.
 */
@Composable
private fun MainDivider(
    modifier: Modifier = Modifier,
) {
    VerticalDivider(
        modifier = modifier, thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground
    )
}