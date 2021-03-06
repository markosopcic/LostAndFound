package com.markosopcic.lostandfound

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.markosopcic.core.routing.Router
import com.markosopcic.core.routing.addFragment
import com.markosopcic.core.routing.removeFragment
import com.markosopcic.mapoptions.ui.MapOptionsFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@IdRes
private const val MAIN_CONTAINER = R.id.activity_main

private const val MAP_CONTAINER = R.id.main_container
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

    override fun requestPermissions(permissions: Array<String>) =
        activity.requestPermissions(permissions, 0)

    override fun showMapOptionsScreen() =
        fragmentManager.addFragment(MAP_CONTAINER, MapOptionsFragment())

    override fun closeMapOptions() {
        fragmentManager.removeFragment(MapOptionsFragment::class.java.simpleName)
    }

}
