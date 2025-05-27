package com.klivvrassesment.ui.models

sealed interface CityListItem {
    data class Header(val letter: Char): CityListItem
    data class CityItem(val city: CityUiModel): CityListItem
}