package com.markosopcic.lostandfound

import android.app.Application
import com.markosopcic.core.di.coreModule
import com.markosopcic.locationlib.di.locationModule
import com.markosopcic.lostandfound.di.appModule
import com.markosopcic.map.di.mapModule
import com.markosopcic.mapoptions.di.mapOptionsModule
import com.markosopcic.reporteditemslib.module.reportedItemsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LostAndFoundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LostAndFoundApplication)
            modules(
                appModule(),
                coreModule(),
                locationModule(),
                mapOptionsModule(),
                mapModule(),
                reportedItemsModule()
            )
        }

        Timber.plant(Timber.DebugTree())
    }
}
