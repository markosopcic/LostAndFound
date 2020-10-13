package com.markosopcic.core.usecase

import io.reactivex.Completable

interface CompletableUseCaseWithParam<T> {

    operator fun invoke(param: T): Completable
}
