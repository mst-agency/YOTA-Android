package app.yota.di

import app.yota.MainActivity
import app.yota.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindsModule {
    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            FragmentsBindsModule::class
        ]
    )
    fun contributeMainActivityInjector(): MainActivity
}