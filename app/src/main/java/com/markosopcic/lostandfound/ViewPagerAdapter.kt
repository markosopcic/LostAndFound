package com.markosopcic.lostandfound

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.markosopcic.lostandfound.map.ui.MapsFragment
import mapoptions.ui.MapOptionsFragment

private const val NAVIGATION_FRAGMENT_NUM = 4

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = NAVIGATION_FRAGMENT_NUM

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MapsFragment()
        1 -> MapOptionsFragment()
        else -> MapsFragment()
    }

}
