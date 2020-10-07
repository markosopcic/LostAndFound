package com.markosopcic.lostandfound.reporteditemslib.source

import com.markosopcic.lostandfound.reporteditemslib.models.ReportedItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReportedItemsSource {

    fun getNearReportedItems(): Flowable<List<ReportedItem>>

    fun updateCurrentCenter(latitude: Double, longitude: Double, range: Double): Completable
}
