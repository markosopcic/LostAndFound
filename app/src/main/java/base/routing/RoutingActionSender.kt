package base.routing

interface RoutingActionSender {

    fun sendRoutingAction(action: (Router) -> Unit)
}
