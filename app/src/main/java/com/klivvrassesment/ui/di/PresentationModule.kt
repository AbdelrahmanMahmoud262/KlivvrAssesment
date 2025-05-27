package com.klivvrassesment.ui.di

import com.klivvrassesment.ui.screens.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {

    viewModelOf(::MainViewModel)
}