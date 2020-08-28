
package app.yota.di

import app.yota.di.scope.PerApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@PerApplication
class Schedulers @Inject constructor() {
    val ui = AndroidSchedulers.mainThread()
    val io = Schedulers.io()
}