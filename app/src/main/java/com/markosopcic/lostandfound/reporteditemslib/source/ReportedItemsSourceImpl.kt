package com.markosopcic.lostandfound.reporteditemslib.source

import com.markosopcic.lostandfound.reporteditemslib.ReportedItemsAPI
import com.markosopcic.lostandfound.reporteditemslib.models.APIReportedItem
import com.markosopcic.lostandfound.reporteditemslib.models.ReportedItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ReportedItemsSourceImpl(private val reportedItemsAPI: ReportedItemsAPI, private val backgroundScheduler: Scheduler) : ReportedItemsSource {

    private val currentCenterProcessor = BehaviorProcessor.create<CenterAndRange>()

    private val nearItemsProcessor = BehaviorProcessor.create<List<ReportedItem>>()

    private val disposable = currentCenterProcessor
            .debounce(1, TimeUnit.SECONDS)
            .flatMap { reportedItemsAPI.getReportedItems(it.longitude, it.latitude, it.range).toFlowable() }
            .map { it.map { it.toDomainModel() } }
            .subscribeOn(backgroundScheduler)
            .doOnError { it.printStackTrace() }
            .subscribe(nearItemsProcessor::onNext, Timber::e)

    override fun getNearReportedItems(): Flowable<List<ReportedItem>> = nearItemsProcessor

    override fun updateCurrentCenter(latitude: Double, longitude: Double, range: Double): Completable = Completable.fromAction {
        currentCenterProcessor.onNext(CenterAndRange(longitude, latitude, range))
    }

    private fun APIReportedItem.toDomainModel() =
            ReportedItem(contactNumber, description, found, id, latitude, longitude, lost, type)

    private data class CenterAndRange(val longitude: Double, val latitude: Double, val range: Double)
}
