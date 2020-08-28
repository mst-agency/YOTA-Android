package app.yota.di

import app.yota.YotaApplication
import app.yota.di.scope.PerApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindsModule::class
    ]
)
interface AppComponent {

    fun inject(app: YotaApplication)
}
