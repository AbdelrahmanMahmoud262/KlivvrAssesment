package com.klivvrassesment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.klivvrassesment.domain.repository.CityRepository
import com.klivvrassesment.ui.models.toUiModel
import com.klivvrassesment.ui.utils.withAlphabeticalHeaders
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val cityRepository: CityRepository,
) : ViewModel() {

    // MutableStateFlow that holds the query value
    private val _searchQuery = MutableStateFlow("")
    // StateFlow that exposes the query value to the UI
    val searchQuery = _searchQuery
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    /**
     * A flow of paginated city data that updates based on the search query.
     * It debounce the search query to avoid unnecessary updates while the user is typing,
     * then fetches and maps the city data from the repository to UI models.
     * The results are cached in the ViewModel's scope to preserve data across configuration changes.
     */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val cities = _searchQuery
        .debounce(300)
        .flatMapLatest {
            cityRepository.searchCountries(it).map { it.map { it.toUiModel() } }
        }
        .withAlphabeticalHeaders()
        .cachedIn(viewModelScope)

    /**
     * Handles UI events triggered by the Main screen.
     *
     * @param event The UI event to handle.
     */
    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.OnSearchQueryChange -> {
                _searchQuery.value = event.query
            }
        }
    }

}
