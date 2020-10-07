package base.ui

import androidx.lifecycle.ViewModel
import base.routing.Router
import base.routing.RoutingActionSender
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseViewModel<BaseViewState : Any>(
    protected val backgroundScheduler: Scheduler,
    protected val mainScheduler: Scheduler,
    private val routingActionSender: RoutingActionSender
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val viewStateProcessors: MutableMap<KClass<BaseViewState>, BehaviorProcessor<BaseViewState>> =
        mutableMapOf()

    protected inline fun <reified T : BaseViewState> observe(viewState: Flowable<T>) =
        observeInternal(T::class as KClass<BaseViewState>, viewState as Flowable<BaseViewState>)

    protected fun observeInternal(
        viewStateClass: KClass<BaseViewState>,
        viewState: Flowable<BaseViewState>
    ) {
        if (viewStateProcessors.contains(viewStateClass)) {
            throw IllegalArgumentException("Flowable of type $viewStateClass already registered!")
        }

        val viewStateProcessor = BehaviorProcessor.create<BaseViewState>()
        viewStateProcessors[viewStateClass] = viewStateProcessor

        disposables.add(
            viewState
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(viewStateProcessor::onNext, Timber::e)
        )
    }

    protected fun execute(command: Completable) = disposables.add(
        command
            .subscribeOn(backgroundScheduler)
            .subscribe(Functions.EMPTY_ACTION, Consumer(Timber::w))
    )

    fun viewStates(): Collection<Flowable<BaseViewState>> = viewStateProcessors.values

    protected fun sendRoutingAction(action: (Router) -> Unit) =
        routingActionSender.sendRoutingAction(action)
}
