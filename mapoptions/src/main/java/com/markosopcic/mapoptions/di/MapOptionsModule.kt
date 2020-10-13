package com.markosopcic.mapoptions.di

import com.markosopcic.core.di.BACKGROUND_SCHEDULER
import com.markosopcic.core.di.MAIN_SCHEDULER
import com.markosopcic.mapoptions.source.MapOptionsSource
import com.markosopcic.mapoptions.source.MapOptionsSourceImpl
import com.markosopcic.mapoptions.ui.MapOptionsViewModel
import com.markosopcic.mapoptions.usecase.GetMapOptions
import com.markosopcic.mapoptions.usecase.UpdateMapOptions
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun mapOptionsModule() = module {

    viewModel {
        MapOptionsViewModel(
            get(),
            get(),
            get(named(BACKGROUND_SCHEDULER)),
            get(named(MAIN_SCHEDULER)),
            get()
        )
    }

    single<MapOptionsSource> {
        MapOptionsSourceImpl(get(), get(named(BACKGROUND_SCHEDULER)))
    }

    single {
        GetMapOptions(get())
    }

    single {
        UpdateMapOptions(get())
    }
}
