package base.routing

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.markosopcic.lostandfound.R
import com.markosopcic.lostandfound.map.ui.MapsFragment

@IdRes
private const val MAIN_CONTAINER = R.id.activity_main

class RouterImpl(
    private val activity: AppCompatActivity,
    private val fragmentManager: FragmentManager
) : Router {
    override fun showMapScreen() {
        val instance = MapsFragment()
        fragmentManager.inTransaction {
            add(MAIN_CONTAINER, instance, instance::class.java.simpleName)
        }
    }

    override fun requestPermissions(permissions: Array<String>) = activity.requestPermissions(permissions, 0)

}
