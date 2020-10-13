package com.markosopcic.core.di

import com.markosopcic.core.routing.RoutingActionSender
import com.markosopcic.core.routing.RoutingHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCHEDULER = "MAIN_SCHEDULER"
const val BACKGROUND_SCHEDULER = "BACKGROUND_SCHEDULER"

fun coreModule() = module {

    single { RoutingHandler }
    single<RoutingActionSender> { RoutingHandler }

    single(named(MAIN_SCHEDULER)) { AndroidSchedulers.mainThread() }

    single(named(BACKGROUND_SCHEDULER)) { Schedulers.io() }

}
