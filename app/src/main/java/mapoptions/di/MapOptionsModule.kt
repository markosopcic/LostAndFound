package mapoptions.di

import base.di.BACKGROUND_SCHEDULER
import base.di.MAIN_SCHEDULER
import mapoptions.source.MapOptionsSource
import mapoptions.source.MapOptionsSourceImpl
import mapoptions.ui.MapOptionsViewModel
import mapoptions.usecase.GetMapOptions
import mapoptions.usecase.UpdateMapOptions
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
