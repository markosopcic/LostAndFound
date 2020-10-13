package com.markosopcic.core.routing

interface RoutingConsumer {

    fun consumeRoutingAction(action: (Router) -> Unit)
}
