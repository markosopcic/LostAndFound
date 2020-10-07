package com.markosopcic.lostandfound.reporteditemslib.usecase

import base.usecase.QueryUseCase
import com.markosopcic.lostandfound.reporteditemslib.models.ReportedItem
import com.markosopcic.lostandfound.reporteditemslib.source.ReportedItemsSource
import io.reactivex.Flowable

class GetNearReportedItems(private val reportedItemsSource: ReportedItemsSource) : QueryUseCase<List<ReportedItem>> {

    override fun invoke(): Flowable<List<ReportedItem>> = reportedItemsSource.getNearReportedItems()
}
