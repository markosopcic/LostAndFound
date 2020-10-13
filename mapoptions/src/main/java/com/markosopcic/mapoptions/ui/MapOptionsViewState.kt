package com.markosopcic.mapoptions.ui

sealed class MapOptionsViewState {

    class Options(val radius: Long, val minDaysAgo: Long, val maxDaysAgo: Long) : MapOptionsViewState()
}
