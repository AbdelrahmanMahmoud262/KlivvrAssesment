package com.klivvrassesment.ui.models

sealed interface CityListItem {
    data class Header(
        val letter: String,
        val isLast: Boolean = false
    ) : CityListItem
    data class CityItem(val city: CityUiModel): CityListItem
}