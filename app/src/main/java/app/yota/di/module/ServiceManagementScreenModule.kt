package app.yota.di.module

import android.content.Context
import android.widget.Toast
import app.yota.di.scope.ViewModelInject
import app.yota.fragment.IServiceManagementScreenRouter
import app.yota.fragment.servicemanagement.ServiceManagementFragment
import app.yota.fragment.servicemanagement.ServiceManagementViewModel
import app.yota.utils.ViewModelFactory
import app.yota.utils.getViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class ServiceManagementScreenModule {

    @Provides
    @ViewModelInject
    fun provideViewModel(
        fragment: ServiceManagementFragment,
        provider: Provider<ServiceManagementViewModel>
    ) = fragment.getViewModel<ServiceManagementViewModel>(ViewModelFactory(provider))

    @Provides
    fun provideServiceManagementScreenRouter(context: Context): IServiceManagementScreenRouter {
        return object : IServiceManagementScreenRouter {
            override fun toCardManagement() {
                Toast.makeText(context, "router to CardManagement", Toast.LENGTH_SHORT).show()
            }

            override fun toDeeplink(deeplink: String) {
                Toast.makeText(context, "router to deeplink: $deeplink", Toast.LENGTH_SHORT).show()
            }
        }
    }
}