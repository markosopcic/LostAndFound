package com.markosopcic.core

import io.reactivex.Completable

fun Completable.subscribeWithOnError(onError: (Throwable) -> Unit) = this.subscribe({}, onError)
