package com.markosopcic.core.routing

interface Router {
    fun showMapScreen()

    fun requestPermissions(permissions: Array<String>)

    fun showMapOptionsScreen()

    fun closeMapOptions()
}
