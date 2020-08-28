package app.yota

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val disposables = CompositeDisposable()

    protected inline fun subscribe(crossinline function: () -> Disposable) {
        subscribe(function())
    }

    protected fun subscribe(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}