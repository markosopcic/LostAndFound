package com.markosopcic.lostandfound.reporteditemslib.usecase

import base.usecase.CompletableUseCaseWithParam
import com.markosopcic.lostandfound.reporteditemslib.source.ReportedItemsSource
import io.reactivex.Completable

class UpdateCurrentMapCenter(private val reportedItemsSource: ReportedItemsSource) : CompletableUseCaseWithParam<CenterAndRange> {
    override fun invoke(param: CenterAndRange): Completable =
        reportedItemsSource.updateCurrentCenter(param.latitude, param.longitude, param.range)

}

data class CenterAndRange(val longitude: Double, val latitude: Double, val range: Double)
