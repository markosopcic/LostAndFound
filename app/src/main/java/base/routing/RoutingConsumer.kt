package base.routing

interface RoutingConsumer {

    fun consumeRoutingAction(action: (Router) -> Unit)
}
