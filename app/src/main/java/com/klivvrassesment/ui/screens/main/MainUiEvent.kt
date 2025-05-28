package com.klivvrassesment.ui.screens.main

/**
 * Defines the sealed interface for UI events that can occur in the main screen.
 * This interface is used to represent different user interactions or system events
 * that the UI needs to react to.
 */
sealed interface MainUiEvent {


    /**
     * Represents a UI event that is triggered when the search query changes.
     *
     * @property query The new search query string.
     */
    data class OnSearchQueryChange(val query: String) : MainUiEvent

}