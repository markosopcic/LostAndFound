package com.markosopcic.mapoptions.source

import android.content.Context
import android.content.SharedPreferences
import com.markosopcic.core.subscribeWithOnError
import com.markosopcic.mapoptions.model.MapOptions
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val MAP_OPTIONS_PREFS = "MAP_OPTIONS_PREFS"
private const val RADIUS_KEY = "RADIUS"
private const val MIN_DAYS_AGO = "MIN_DAYS_AGO"
private const val MAX_DAYS_AGO = "MAX_DAYS_AGO"

private const val DEFAULT_RADIUS = 1000L
private const val DEFAULT_MIN_DAYS_AGO = 0L
private const val DEFAULT_MAX_DAYS_AGO = 30L

class MapOptionsSourceImpl(context: Context, backgroundScheduler: Scheduler) : MapOptionsSource {

    private val optionsPrefs: SharedPreferences = context.getSharedPreferences(MAP_OPTIONS_PREFS, Context.MODE_PRIVATE)

    private val optionsProcessor: BehaviorProcessor<MapOptions> = BehaviorProcessor.createDefault(getDefaultOptions())

    private val updateOptions = PublishProcessor.create<MapOptions>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            updateOptions
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(backgroundScheduler)
                .distinctUntilChanged()
                .switchMapCompletable(::setMapOptions)
                .subscribeWithOnError(Timber::d)
        )
    }

    override fun getMapOptions(): Flowable<MapOptions> = optionsProcessor

    override fun setMapOptions(options: MapOptions): Completable = Completable.fromAction {
        optionsPrefs
            .edit()
            .putLong(RADIUS_KEY, options.radiusMeters)
            .putLong(MIN_DAYS_AGO, options.minDaysAgo)
            .putLong(MAX_DAYS_AGO, options.maxDaysAgo)
            .apply()
        optionsProcessor.onNext(options)
    }

    private fun getDefaultOptions(): MapOptions =
        optionsPrefs.run {
            MapOptions(getLong(RADIUS_KEY, DEFAULT_RADIUS), getLong(MIN_DAYS_AGO, DEFAULT_MIN_DAYS_AGO), getLong(MAX_DAYS_AGO, DEFAULT_MAX_DAYS_AGO))
        }

}
