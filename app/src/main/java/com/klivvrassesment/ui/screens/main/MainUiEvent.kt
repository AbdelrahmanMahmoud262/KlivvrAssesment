package com.klivvrassesment.ui.screens.main

sealed interface MainUiEvent {

    data class OnSearchQueryChange(val query: String) : MainUiEvent

}