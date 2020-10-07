package base.routing

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.markosopcic.lostandfound.MainActivity
import com.markosopcic.lostandfound.R
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@IdRes
private const val MAIN_CONTAINER = R.id.activity_main
private const val MAP_SCREEN_INDEX = 0
class RouterImpl(
    private val activity: AppCompatActivity,
    private val fragmentManager: FragmentManager
) : Router {
    override fun showMapScreen() {
        if (activity is MainActivity) {
            activity.main_container_viewPager.currentItem = MAP_SCREEN_INDEX
        } else {
            Timber.w("Setting navigation screen from wrong activity!")
        }
    }

    override fun requestPermissions(permissions: Array<String>) = activity.requestPermissions(permissions, 0)

}
