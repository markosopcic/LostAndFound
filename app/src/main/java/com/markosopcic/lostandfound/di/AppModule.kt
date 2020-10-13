package com.markosopcic.lostandfound.di

import androidx.appcompat.app.AppCompatActivity
import com.markosopcic.core.routing.Router
import com.markosopcic.lostandfound.RouterImpl
import org.koin.dsl.module

fun appModule() = module {
    factory<Router> {
        val activity: AppCompatActivity = it[0]
        RouterImpl(activity, activity.supportFragmentManager)
    }
}
