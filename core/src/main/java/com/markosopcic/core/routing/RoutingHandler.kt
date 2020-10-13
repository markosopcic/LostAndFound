package com.markosopcic.core.routing

import timber.log.Timber
import java.util.*

object RoutingHandler : RoutingActionSender {

    private val lock = Any()

    private val queuedActions: Queue<(Router) -> Unit> = LinkedList()

    private var consumer: RoutingConsumer? = null

    private fun enqueueAction(action: (Router) -> Unit): Unit =
        synchronized(lock) { consumer?.consumeRoutingAction(action) ?: queuedActions.add(action) }

    fun setRoutingConsumer(consumer: RoutingConsumer) {
        if (this.consumer != null) {
            Timber.w("Setting routing consumer while another still active!")
        }

        if (this.consumer == consumer) {
            Timber.w("Setting same consumer again!")
        }

        synchronized(lock) {
            this.consumer = consumer
            while (queuedActions.size > 0) consumer.consumeRoutingAction(queuedActions.remove())
        }
    }

    fun removeRoutingConsumer(consumer: RoutingConsumer) {
        if (this.consumer != consumer) {
            Timber.w("Can't remove different consumer!")
        } else {
            this.consumer = null
        }
    }

    override fun sendRoutingAction(action: (Router) -> Unit) = enqueueAction(action)
}
