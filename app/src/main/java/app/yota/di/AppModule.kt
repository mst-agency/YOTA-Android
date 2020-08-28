package app.yota.di

import android.app.Application
import android.content.Context
import app.yota.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule.BindsModule::class])
class AppModule(private val app: Application) {
    @Provides
    @PerApplication
    fun provideContext(): Context = app.applicationContext

    @Module
    abstract class BindsModule {
    }
}