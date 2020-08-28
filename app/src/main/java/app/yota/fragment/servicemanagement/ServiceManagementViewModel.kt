package app.yota.fragment.servicemanagement

import androidx.lifecycle.MutableLiveData
import app.yota.BaseViewModel
import app.yota.di.Schedulers
import app.yota.domain.repository.IAccountRepository
import javax.inject.Inject

class ServiceManagementViewModel @Inject constructor(
    private val schedulers: Schedulers,
    private val accountRepository: IAccountRepository
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<State>()

    val stateLiveData
        get() = _stateLiveData

    init {
        subscribe {
            accountRepository.getAccountData()
                .observeOn(schedulers.ui)
                .subscribeOn(schedulers.io)
                .doOnSubscribe {
                    _stateLiveData.postValue(State.Loading)
                }
                .subscribe { data, _ ->
                    if (data != null) {
                        _stateLiveData.postValue(
                            State.Content(
                                data.cardNumber.takeLast(4).toInt(),
                                data.money
                            )
                        )
                    }
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Content(val cardLastNumber: Int, val money: Float) : State()
    }
}