package app.yota.di.module

import app.yota.di.scope.ViewModelInject
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

}