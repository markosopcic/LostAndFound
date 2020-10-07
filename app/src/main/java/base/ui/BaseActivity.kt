package base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import base.routing.Router
import base.routing.RoutingConsumer
import base.routing.RoutingHandler
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

open class BaseActivity(@LayoutRes activityId: Int) : AppCompatActivity(activityId), RoutingConsumer {

    protected val router: Router by inject { parametersOf(this) }

    protected val routingHandler = RoutingHandler

    override fun consumeRoutingAction(action: (Router) -> Unit) = action(router)

    open fun initialiseView() {

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initialiseView()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        routingHandler.setRoutingConsumer(this)
    }

    override fun onPause() {
        super.onPause()
        routingHandler.removeRoutingConsumer(this)
    }

}
