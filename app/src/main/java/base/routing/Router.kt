package base.routing

interface Router {
    fun showMapScreen()

    fun requestPermissions(permissions: Array<String>)

    fun showMapOptionsScreen()

    fun closeMapOptions()
}
