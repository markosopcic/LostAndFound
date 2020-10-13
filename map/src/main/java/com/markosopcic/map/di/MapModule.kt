package com.markosopcic.map.di

import com.markosopcic.core.di.BACKGROUND_SCHEDULER
import com.markosopcic.core.di.MAIN_SCHEDULER
import com.markosopcic.map.ui.MapViewModel
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
