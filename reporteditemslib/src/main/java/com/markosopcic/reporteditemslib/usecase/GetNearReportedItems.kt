package com.markosopcic.reporteditemslib.usecase

import com.markosopcic.core.usecase.QueryUseCase
import com.markosopcic.reporteditemslib.models.ReportedItem
import com.markosopcic.reporteditemslib.source.ReportedItemsSource
import io.reactivex.Flowable

class GetNearReportedItems(private val reportedItemsSource: ReportedItemsSource) :
    QueryUseCase<List<ReportedItem>> {

    override fun invoke(): Flowable<List<ReportedItem>> = reportedItemsSource.getNearReportedItems()
}
