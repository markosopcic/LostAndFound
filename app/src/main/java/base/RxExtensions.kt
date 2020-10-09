package base

import io.reactivex.Completable

fun Completable.subscribeWithOnError(onError: (Throwable) -> Unit) = this.subscribe({}, onError)
