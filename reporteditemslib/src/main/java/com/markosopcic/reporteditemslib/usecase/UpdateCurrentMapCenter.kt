package com.markosopcic.reporteditemslib.usecase

import com.markosopcic.core.usecase.CompletableUseCaseWithParam
import com.markosopcic.reporteditemslib.source.ReportedItemsSource
import io.reactivex.Completable

class UpdateCurrentMapCenter(private val reportedItemsSource: ReportedItemsSource) :
        CompletableUseCaseWithParam<CenterAndRange> {
    override fun invoke(param: CenterAndRange): Completable =
            reportedItemsSource.updateCurrentCenter(param.latitude, param.longitude, param.range)

}

data class CenterAndRange(val longitude: Double, val latitude: Double, val range: Double)
