package com.klivvrassesment.ui.utils

import androidx.compose.ui.unit.dp

object Constants {

    val HEADER_SIZE = 60.dp

    fun String.getFlagUrl(): String = "https://flagcdn.com/36x27/${this.lowercase()}.png"
}