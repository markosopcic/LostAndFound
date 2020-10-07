package com.markosopcic.lostandfound

import android.app.Application
import base.di.baseModule
import com.markosopcic.lostandfound.locationlib.di.locationModule
import com.markosopcic.lostandfound.map.di.mapModule
import com.markosopcic.lostandfound.reporteditemslib.module.reportedItemsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LostAndFoundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LostAndFoundApplication)
            modules(
                baseModule(),
                mapModule(),
                locationModule(),
                reportedItemsModule()
            )
        }

        Timber.plant(Timber.DebugTree())
    }
}
