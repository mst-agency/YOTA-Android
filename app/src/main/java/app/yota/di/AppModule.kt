package app.yota.di

import android.app.Application
import android.content.Context
import app.yota.data.FakeAccountRepository
import app.yota.di.scope.PerApplication
import app.yota.domain.repository.IAccountRepository
import app.yota.utils.CurrencyFormatter
import app.yota.utils.ICurrencyFormatter
import dagger.Binds
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

    @Module
    abstract class BindsModule {

        @Binds
        @PerApplication
        abstract fun bindCurrencyFormatter(impl: CurrencyFormatter): ICurrencyFormatter
    }
}