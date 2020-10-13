package com.markosopcic.core.routing

interface RoutingActionSender {

    fun sendRoutingAction(action: (Router) -> Unit)
}
