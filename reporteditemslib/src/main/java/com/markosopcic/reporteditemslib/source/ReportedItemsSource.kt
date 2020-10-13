package com.markosopcic.reporteditemslib.source

import com.markosopcic.reporteditemslib.models.ReportedItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReportedItemsSource {

    fun getNearReportedItems(): Flowable<List<ReportedItem>>

    fun updateCurrentCenter(latitude: Double, longitude: Double, range: Double): Completable
}
