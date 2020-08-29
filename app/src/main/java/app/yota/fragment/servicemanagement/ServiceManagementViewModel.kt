package app.yota.fragment.servicemanagement

import androidx.lifecycle.MutableLiveData
import app.yota.BaseViewModel
import app.yota.di.Schedulers
import app.yota.domain.repository.IAccountRepository
import app.yota.fragment.IServiceManagementScreenRouter
import app.yota.view.notifications.carousel.CarouselViewHolderModel
import javax.inject.Inject

class ServiceManagementViewModel @Inject constructor(
    private val router: IServiceManagementScreenRouter,
    private val schedulers: Schedulers,
    private val accountRepository: IAccountRepository
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<State>()

    private val _accountLiveData = MutableLiveData<AccountData>()
    private val _notificationLiveData = MutableLiveData<List<CarouselViewHolderModel>>()

    val stateLiveData
        get() = _stateLiveData

    val accountLiveData
        get() = _accountLiveData

    val notificationsLiveData
        get() = _notificationLiveData

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
                        _stateLiveData.postValue(State.Content)
                        _accountLiveData.postValue(
                            AccountData(
                                data.cardNumber.takeLast(4).toInt(),
                                data.money
                            )
                        )
                        _notificationLiveData.postValue(
                            listOf(
                                CarouselViewHolderModel(),
                                CarouselViewHolderModel()
                            )
                        )
                    }
                }
        }
    }

    fun onCardNumberClick() {
        router.toCardManagement()
    }

    sealed class State {
        object Loading : State()
        object Content : State()
    }

    data class AccountData(val cardLastNumber: Int, val money: Float)
}