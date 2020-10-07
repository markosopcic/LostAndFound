package base.usecase

import io.reactivex.Completable

interface CompletableUseCase {

    operator fun invoke(): Completable
}
