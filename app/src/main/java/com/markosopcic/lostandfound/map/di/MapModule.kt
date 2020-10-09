package com.markosopcic.lostandfound.map.di

import base.di.BACKGROUND_SCHEDULER
import base.di.MAIN_SCHEDULER
import com.markosopcic.lostandfound.map.ui.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun mapModule() = module {

    viewModel {
        MapViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(named(BACKGROUND_SCHEDULER)),
                get(named(MAIN_SCHEDULER)),
                get()
        )
    }
}
