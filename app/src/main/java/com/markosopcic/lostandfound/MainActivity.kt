package com.markosopcic.lostandfound

import base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun initialiseView() {
        val adapter = ViewPagerAdapter(this)
        main_container_viewPager.adapter = adapter
        mainActivity_bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.baseMenu_map -> main_container_viewPager.currentItem = 0
                R.id.baseMenu_search -> main_container_viewPager.currentItem = 1
                R.id.baseMenu_myReportedItems -> main_container_viewPager.currentItem = 2
                R.id.baseMenu_myInfo -> main_container_viewPager.currentItem = 3
            }
            true
        }

        mainActivity_bottomNavigationMenu.selectedItemId = R.id.baseMenu_map
    }
}
