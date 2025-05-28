package com.klivvrassesment.ui.utils

import androidx.compose.ui.unit.dp

object Constants {

    /**
     * Represents the standard height for header components within the application.
     * This value is expressed in density-independent pixels (dp) to ensure consistent
     * sizing across different screen densities.
     */
    val HEADER_SIZE = 60.dp

    /**
     * Extension function to get the flag URL for a given country code.
     *
     * This function takes a country code as a string and returns the URL of the flag image
     * from flagcdn.com. The country code is converted to lowercase before being used in the URL.
     * The flag images are 36x27 pixels.
     *
     * Example:
     * ```
     * "US".getFlagUrl() // returns "https://flagcdn.com/36x27/us.png"
     * ```
     *
     * @receiver The country code string.
     * @return The URL of the flag image.
     */
    fun String.getFlagUrl(): String = "https://flagcdn.com/36x27/${this.lowercase()}.png"
}