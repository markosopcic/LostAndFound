package com.markosopcic.core.usecase

import io.reactivex.Flowable

interface QueryUseCase<T> {

    operator fun invoke(): Flowable<T>
}
