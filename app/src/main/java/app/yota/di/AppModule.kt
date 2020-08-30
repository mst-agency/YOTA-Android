package app.yota.di

import android.app.Application
import android.content.Context
import app.yota.data.repository.FakeAccountRepository
import app.yota.data.repository.FaceNotificationsRepository
import app.yota.di.scope.PerApplication
import app.yota.domain.repository.IAccountRepository
import app.yota.domain.repository.INotificationsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule.BindsModule::class])
class AppModule(private val app: Application) {
    @Provides
    @PerApplication
    fun provideContext(): Context = app.applicationContext

    @Provides
    fun provideAccountRepository(): IAccountRepository {
        return FakeAccountRepository()
    }

    @Provides
    fun provideNotificationsRepository(): INotificationsRepository {
        return FaceNotificationsRepository()
    }

    @Module
    abstract class BindsModule {

    }
}