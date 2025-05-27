package com.klivvrassesment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.klivvrassesment.domain.repository.CityRepository
import com.klivvrassesment.ui.models.toUiModel
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val cities = _searchQuery
        .debounce(300)
        .flatMapLatest {
            cityRepository.searchCountries(it).map { it.map { it.toUiModel() } }
        }.cachedIn(viewModelScope)

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.OnSearchQueryChange -> {
                _searchQuery.value = event.query
            }
        }
    }

}
