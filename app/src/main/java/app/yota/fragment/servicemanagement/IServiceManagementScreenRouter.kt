package app.yota.fragment.servicemanagement

interface IServiceManagementScreenRouter {
    fun toCardManagement()
    fun toDeeplink(deeplink: String)
}