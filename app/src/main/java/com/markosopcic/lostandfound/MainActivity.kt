package com.markosopcic.lostandfound

import base.routing.Router
import base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val adapter by lazy { ViewPagerAdapter(this) }

    override fun initialiseView() {
        main_container_viewPager.adapter = adapter
        main_container_viewPager.isUserInputEnabled = false
        mainActivity_bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.baseMenu_map -> consumeRoutingAction(Router::showMapScreen)
                R.id.baseMenu_search -> Unit
                R.id.baseMenu_myReportedItems -> Unit
                R.id.baseMenu_myInfo -> Unit
            }
            true
        }

        mainActivity_bottomNavigationMenu.selectedItemId = R.id.baseMenu_map
    }
}
