package app.yota.fragment.servicemanagement

import androidx.lifecycle.MutableLiveData
import app.yota.BaseViewModel
import app.yota.di.Schedulers
import app.yota.domain.entity.AccountData
import app.yota.domain.entity.Notification
import app.yota.domain.repository.IAccountRepository
import app.yota.domain.repository.INotificationsRepository
import app.yota.view.notifications.carousel.ICarouselViewHolderModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ServiceManagementViewModel @Inject constructor(
    private val router: IServiceManagementScreenRouter,
    private val schedulers: Schedulers,
    private val accountRepository: IAccountRepository,
    private val notificationsRepository: INotificationsRepository
) : BaseViewModel() {

    private val reloadDataSubject = PublishSubject.create<Unit>()

    private val _stateLiveData = MutableLiveData<State>()

    private val _accountLiveData = MutableLiveData<AccountModel>()
    private val _notificationLiveData = MutableLiveData<MutableList<CarouselViewHolderModel>>()

    val stateLiveData
        get() = _stateLiveData

    val accountLiveData
        get() = _accountLiveData

    val notificationsLiveData
        get() = _notificationLiveData

    init {
        subscribe {
            reloadDataSubject
                .startWith(Unit)
                .observeOn(schedulers.io)
                .switchMap {
                    accountRepository.getAccountData()
                        .zipWith<List<Notification>, Pair<AccountData, List<Notification>>>(
                            notificationsRepository.getNotifications(),
                            BiFunction { t1, t2 ->
                                t1 to t2
                            })
                        .doOnSubscribe { stateLiveData.postValue(State.Loading) }
                        .doOnError {
                            stateLiveData.postValue(State.Error)
                        }
                        .toObservable()
                        .onErrorResumeNext(Observable.empty())
                }
                .observeOn(schedulers.ui)
                .subscribeOn(schedulers.io)
                .subscribe { (accountData, notifications) ->
                    _stateLiveData.postValue(State.Content)
                    _accountLiveData.postValue(
                        AccountModel(
                            accountData.cardNumber.takeLast(4).toInt(),
                            accountData.money
                        )
                    )
                    _notificationLiveData.postValue(
                        notifications.sortedBy { it.priority }.map {
                            CarouselViewHolderModel(
                                id = it.id,
                                closable = it.closable,
                                text = it.text,
                                actionButtonText = it.button?.text,
                                rawNotification = it
                            )
                        }.toMutableList()
                    )
                }
        }
    }

    fun onRetryClick() = reloadDataSubject.onNext(Unit)

    fun onNotificationActionButtonClick(id: Long) {
        val notification = _notificationLiveData.value?.first { it.id == id }
        notification?.rawNotification?.button?.deeplink?.let { deeplink ->
            router.toDeeplink(deeplink)
        }
    }

    fun onNotificationCloseButtonClick(id: Long) {
        val notification = _notificationLiveData.value?.first { it.id == id }
        _notificationLiveData.value?.remove(notification)?.let {
            _notificationLiveData.postValue(_notificationLiveData.value)
        }
    }

    fun onCardNumberClick() {
        router.toCardManagement()
    }

    sealed class State {
        object Loading : State()
        object Content : State()
        object Error : State()
    }

    data class AccountModel(val cardLastNumber: Int, val money: Float)

    class CarouselViewHolderModel(
        override val id: Long,
        override val closable: Boolean,
        override val text: String,
        override val actionButtonText: String?,
        val rawNotification: Notification
    ) : ICarouselViewHolderModel
}