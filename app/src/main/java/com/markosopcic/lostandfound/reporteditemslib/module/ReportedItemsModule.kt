package com.markosopcic.lostandfound.reporteditemslib.module

import base.di.BACKGROUND_SCHEDULER
import com.markosopcic.lostandfound.reporteditemslib.ReportedItemsAPI
import com.markosopcic.lostandfound.reporteditemslib.source.ReportedItemsSource
import com.markosopcic.lostandfound.reporteditemslib.source.ReportedItemsSourceImpl
import com.markosopcic.lostandfound.reporteditemslib.source.UnsafeOkHttpClient
import com.markosopcic.lostandfound.reporteditemslib.usecase.GetNearReportedItems
import com.markosopcic.lostandfound.reporteditemslib.usecase.UpdateCurrentMapCenter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://10.0.2.2:5000"

fun reportedItemsModule() = module {

    single {
        Retrofit.Builder()
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient().build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ReportedItemsAPI::class.java)
    }

    single<ReportedItemsSource> { ReportedItemsSourceImpl(get(), get(named(BACKGROUND_SCHEDULER))) }

    single { GetNearReportedItems(get()) }

    single { UpdateCurrentMapCenter(get()) }
}
