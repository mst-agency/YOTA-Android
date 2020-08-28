package app.yota.di

import app.yota.di.module.ServiceManagementScreenModule
import app.yota.di.scope.PerFragment
import app.yota.fragment.servicemanagement.ServiceManagementFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface FragmentsBindsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [ServiceManagementScreenModule::class])
    fun contributeServiceManagementFragmentInjector(): ServiceManagementFragment
}