package base.di

import androidx.appcompat.app.AppCompatActivity
import base.routing.Router
import base.routing.RouterImpl
import base.routing.RoutingActionSender
import base.routing.RoutingHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCHEDULER = "MAIN_SCHEDULER"
const val BACKGROUND_SCHEDULER = "BACKGROUND_SCHEDULER"

fun baseModule() = module {

    single { RoutingHandler }
    single<RoutingActionSender> { RoutingHandler }

    single(named(MAIN_SCHEDULER)) { AndroidSchedulers.mainThread() }

    single(named(BACKGROUND_SCHEDULER)) { Schedulers.io() }

    factory<Router> {
        val activity: AppCompatActivity = it[0]
        RouterImpl(activity, activity.supportFragmentManager)
    }
}
