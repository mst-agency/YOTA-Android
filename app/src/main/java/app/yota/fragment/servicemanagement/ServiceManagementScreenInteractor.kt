package app.yota.fragment.servicemanagement

import app.yota.domain.entity.AccountData
import app.yota.domain.entity.Notification
import app.yota.domain.entity.Profile
import app.yota.domain.repository.IAccountRepository
import app.yota.domain.repository.INotificationsRepository
import app.yota.domain.repository.IProfileRepository
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class ServiceManagementScreenInteractor @Inject constructor(
    private val accountRepository: IAccountRepository,
    private val profileRepository: IProfileRepository,
    private val notificationsRepository: INotificationsRepository
) {

    fun load(): Single<Data> {
        return Single.zip<AccountData, List<Notification>, Profile, Data>(
            accountRepository.getAccountData(),
            notificationsRepository.getNotifications(),
            profileRepository.getProfile(),
            Function3 { t1, t2, t3 ->
                Data(t1, t2, t3)
            })
    }

    data class Data(
        val accountData: AccountData,
        val notification: List<Notification>,
        val profile: Profile
    )
}